package com.education.uv.Dao.Impl;

import com.education.uv.Dao.LoginDao;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class LoginDaoImpl implements LoginDao{
    @Resource
    JdbcTemplate jdbctemple;

    String md5Key = "oeifhoifop0921090@)(&";

    @Override
    public int registeUser(Map<String, Object> parameter) {

        return 0;
    }

    @Override
    public List<Map<String, Object>> loginUser(Map<String, Object> parameter) {

        String username = parameter.get("username").toString();
        String password = parameter.get("password").toString() + this.md5Key;

        //根据用户名查询是否有用户
        String sql = "SELECT id, username, password, rules FROM uv_login WHERE username = ?";
        List<Object> query = new ArrayList<Object>();
        query.add(username);
        List<Map<String, Object>> res = null;
        try {
            res = jdbctemple.queryForList(sql,query.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        //没有用户
        if (res.size() == 0) {
            return res;
        }
        Map<String, Object> user = res.get(0);
        String dataPwd = user.get("password").toString();
        user.remove("password");

        // 生成一个MD5加密计算摘要
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            String pwd = new BigInteger(1, md.digest()).toString(16);
            System.out.println(""+pwd);
            //获取的相比较
            if (dataPwd.equals(pwd)) {
                String token = UUID.randomUUID().toString();
                user.put("token",token);
                //讲数据替换到里面
                String updateToken = "UPDATE uv_login SET token = ? WHERE username = ?";
                List<Object> tokenQuery = new ArrayList<Object>();
                tokenQuery.add(token);
                tokenQuery.add(username);
                try {
                    jdbctemple.update(updateToken,tokenQuery.toArray());
                } catch (DataAccessException e) {
                    e.printStackTrace();
                }
            } else {
                user.clear();
                user.put("message","密码错误");
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return res;
    }

    @Override
    public int changePassword(Map<String, Object> parameter) {
        String userId = parameter.get("userId").toString();
        //老密码做MD5 加密
        String oldPwd = parameter.get("oldPwd").toString() + this.md5Key;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(oldPwd.getBytes());
        oldPwd = new BigInteger(1, md.digest()).toString(16);

        String getUserSql = "SELECT password FROM uv_login WHERE id = ?";
        List<Object> query = new ArrayList<Object>();
        query.add(userId);

        List<Map<String, Object>> userList = jdbctemple.queryForList(getUserSql, query.toArray());
        if (userList.size() > 0) {
            Map<String, Object> userMap = userList.get(0);
            String originPwd = userMap.get("password").toString();
            if (originPwd.equals(oldPwd)) {
                //与原数据相同  进行修改
                String newPwd = parameter.get("newPwd").toString() + this.md5Key;
                try {
                    md = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                md.update(newPwd.getBytes());
                newPwd = new BigInteger(1, md.digest()).toString(16);
                String changePwdSql = "UPDATE uv_login SET password = ? WHERE id = ?";
                List<Object> changePwdquery = new ArrayList<Object>();
                changePwdquery.add(newPwd);
                changePwdquery.add(userId);
                int res = jdbctemple.update(changePwdSql, changePwdquery.toArray());
                return res;
            } else {
                //与初始密码不相同
                return 3;
            }

        }
        return 0;
    }
}
