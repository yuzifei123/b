package com.education.uv.Dao.Impl;

import com.education.uv.Dao.MemberDao;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class MemberDaoImpl implements MemberDao {

    @Resource
    JdbcTemplate jdbctemple;

    @Override
    public List<Map<String, Object>> getMemberList(int pageSize, int page, Map<String, Object> searchMap) {
        page = page==0 ? 1:page;
        pageSize = pageSize == 0 ? 10 : pageSize;
        StringBuffer sql = new StringBuffer("SELECT p.memberId , p.name,p.phone,s.education,s.isPartJob,s.salary ,p.icon,s.isOurMember,s.school " +
                "FROM uv_member s LEFT JOIN uv_person AS p ON s.id = p.memberId  LIMIT ?,?");
        List<Object> query = new ArrayList<Object>();
        query.add(pageSize *(page - 1));
        query.add(pageSize);

        List<Map<String, Object>> data = jdbctemple.queryForList(sql.toString(),query.toArray());
        return data;
    }

    @Override
    public String getMemberTotal(Map<String, Object> searchMap) {
        String countSql = "SELECT COUNT(*) AS total  FROM uv_member s LEFT JOIN uv_person  p ON s.id = p.memberId";
        Map<String, Object> map = jdbctemple.queryForMap(countSql);
        return map.get("total").toString();
    }

    @Override
    public List<Map<String, Object>> getMemberDetails(String memberId) {
        StringBuffer sql = new StringBuffer("SELECT p.memberId ,p.name ,p.phone, p.birthday,p.icon,p.age,p.gender ," +
                "p.remark, p.create_time,m.school,m.education,m.isOurMember,m.isPartJob,m.salary,m.employTime" +
                " FROM uv_member m LEFT JOIN uv_person p ON p.memberId = m.id WHERE m.id = ?");
        List<Object> studetnQuery = new ArrayList<Object>();
        studetnQuery.add(memberId);

        List<Map<String, Object>> data = jdbctemple.queryForList(sql.toString(),studetnQuery.toArray());
        return data;
    }

    @Transactional
    @Override
    public int insertMember(Map<String, Object> parameter) {
        String name = parameter.get("name").toString();
        String birthDay = parameter.get("birthday").toString();
        String employTime = parameter.get("employTime").toString();
        int age  = Integer.parseInt(parameter.get("age").toString());
        int gender = Integer.parseInt(parameter.get("gender").toString());
        String school = parameter.get("school").toString();
        String phone = parameter.get("phone").toString();
        String education = parameter.get("education").toString();
        int salary = Integer.parseInt(parameter.get("salary").toString());
        String icon = "";
        if (parameter.containsKey("icon")) {
            icon = parameter.get("icon").toString();
        }


        int isOurMember = Integer.parseInt(parameter.get("isOurMember").toString());
        int isPartJob = Integer.parseInt(parameter.get("isPartJob").toString());
        String remark = parameter.get("remark").toString();

        long time = System.currentTimeMillis()/1000;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

        StringBuffer studentSql = new StringBuffer("INSERT INTO uv_member (education , school , isOurMember,isPartJob,salary,employTime) VALUES (?,?,?,?,?,?)");

        List<Object> memberQuery = new ArrayList<Object>();
        memberQuery.add(education);
        memberQuery.add(school);
        memberQuery.add(isOurMember);
        memberQuery.add(isPartJob);
        memberQuery.add(salary);
        memberQuery.add(employTime);
//        插入到memberQuery表中 并且返回主键id
        long generatedId = 0;
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbctemple.update(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                    PreparedStatement ps = connection.prepareStatement(studentSql.toString(), Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, education);
                    ps.setString(2, school);
                    ps.setString(3, isOurMember+"");
                    ps.setString(4, isPartJob+"");
                    ps.setString(5, salary+"");
                    ps.setString(6, employTime);
                    return ps;
                }
            }, keyHolder);
            generatedId = keyHolder.getKey().longValue();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        //插入到person 表中
        StringBuffer personSql = new StringBuffer("INSERT INTO uv_person (name , icon , birthday,phone,age,gender,remark,create_time,timestamp,memberId) VALUES (?,?,?,?,?,?,?,?,?,?)");
        List<Object> personQuery = new ArrayList<Object>();
        personQuery.add(name);
        personQuery.add(icon);
        personQuery.add(birthDay);
        personQuery.add(phone);
        personQuery.add(age);
        personQuery.add(gender);
        personQuery.add(remark);
        personQuery.add(df.format(new Date()));
        personQuery.add(time);
        personQuery.add(generatedId);

        int personResult = 0;
        try {
            personResult = jdbctemple.update(personSql.toString(),personQuery.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return personResult;
    }

    @Transactional
    @Override
    public int updateMember(Map<String, Object> parameter) {
        String name = parameter.get("name").toString();
        String birthDay = parameter.get("birthday").toString();
        String employTime = parameter.get("employTime").toString();
        int age  = Integer.parseInt(parameter.get("age").toString());
        int gender = Integer.parseInt(parameter.get("gender").toString());
        String school = parameter.get("school").toString();
        String phone = parameter.get("phone").toString();
        String education = parameter.get("education").toString();
        int salary = Integer.parseInt(parameter.get("salary").toString());
        String icon = "";
        if (parameter.containsKey("icon")) {
            icon = parameter.get("icon").toString();
        }


        int isOurMember = Integer.parseInt(parameter.get("isOurMember").toString());
        int isPartJob = Integer.parseInt(parameter.get("isPartJob").toString());

        String memberId = parameter.get("memberId").toString();

        String remark = parameter.get("remark").toString();

        StringBuffer personSql = new StringBuffer("UPDATE uv_person p set p.name = ? ,");
        //插入到person 表中
        List<Object> personQuery = new ArrayList<Object>();
        personQuery.add(name);

        if (icon.length() != 0) {
            personQuery.add(icon);
            personSql.append("p.icon = ?,");
        }
        personSql.append(" p.birthday = ? ,p.phone = ? ,p.age = ? ,p.gender = ?," +
                " p.remark = ? " +
                " WHERE p.memberId = ?");
        personQuery.add(birthDay);
        personQuery.add(phone);
        personQuery.add(age);
        personQuery.add(gender);
        personQuery.add(remark);
        personQuery.add(memberId);

        StringBuffer memberSql = new StringBuffer("UPDATE uv_member p set p.education = ? ,p.school = ?, " +
                "p.isOurMember = ? ,p.isPartJob = ? ,p.salary = ? ,p.employTime = ?  " +
                "WHERE p.id = ?");
        List<Object> memberQuery = new ArrayList<Object>();
        memberQuery.add(education);
        memberQuery.add(school);
        memberQuery.add(isOurMember);
        memberQuery.add(isPartJob);
        memberQuery.add(salary);
        memberQuery.add(employTime);
        memberQuery.add(memberId);

        int personResult = 0;
        try {
            jdbctemple.update(memberSql.toString(),memberQuery.toArray());
            personResult = jdbctemple.update(personSql.toString(),personQuery.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return personResult;
    }

    @Override
    public int deleteMember(String memberId) {

        String deletePerrsonSql = "DELETE FROM uv_person WHERE memberId = ?";

        StringBuffer sql = new StringBuffer("DELETE FROM uv_member WHERE id = ?");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(memberId);

        int result = 0;
        try {
            jdbctemple.update(deletePerrsonSql.toString(),queryList.toArray());
            result = jdbctemple.update(sql.toString(),queryList.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
}
