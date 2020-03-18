package com.education.uv.Dao.Impl;

import com.education.uv.Dao.TypeInDao;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class TypeInDaoImpl implements TypeInDao {

    @Resource
    JdbcTemplate jdbctemple;

    @Override
    public List<Map<String, Object>> getGoodsList(int isOn) {
        StringBuffer sql = new StringBuffer("SELECT u.id,u.name,u.goodsImage ,u.price ,u.isOn FROM uv_goods_info u ");
        if (isOn == 1) {//1是上架的  0 是所有的
            sql.append("WHERE isOn = 1;");
        }
        List<Map<String, Object>> data = jdbctemple.queryForList(sql.toString());
        return data;
    }

    @Override
    public int insertGoods(Map<String, Object> parameter) {
        String name = parameter.get("name").toString();
        double price  = Double.parseDouble(parameter.get("price").toString());
        int isOn = Integer.parseInt(parameter.get("isOn").toString());
        long time = System.currentTimeMillis()/1000;
        String url = parameter.get("imageUrl").toString();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        StringBuffer sql = new StringBuffer("INSERT INTO uv_goods_info (name , price , isOn,goodsImage,create_time,timestamp) VALUES (?,?,?,?,?,?)");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(name);
        queryList.add(price);
        queryList.add(isOn);
        queryList.add(url);
        queryList.add(df.format(new Date()));
        queryList.add(time);

        int result = 0;
        try {
            result = jdbctemple.update(sql.toString(),queryList.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int updateGoods(Map<String, Object> parameter) {
        String name = parameter.get("name").toString();
        double price  = Double.parseDouble(parameter.get("price").toString());
        int isOn = Integer.parseInt(parameter.get("isOn").toString());
        int id = Integer.parseInt(parameter.get("id").toString());
        String url = null;
        if (parameter.containsKey("imageUrl")) {
            url = parameter.get("imageUrl").toString();
        }

        StringBuffer sql = null;

        List<Object> queryList = new ArrayList<Object>();
        queryList.add(name);
        queryList.add(price);
        queryList.add(isOn);

        if ( url != null && url.length() > 0) {//更改图片
            sql = new StringBuffer("UPDATE uv_goods_info u set u.name = ? ,u.price = ?, u.isOn = ? ,u.goodsImage = ? WHERE u.id = ?");
            queryList.add(url);
        } else {
            sql = new StringBuffer("UPDATE uv_goods_info u set u.name = ? ,u.price = ?, u.isOn = ? WHERE u.id = ?");
        }

        queryList.add(id);
        int result = 0;
        try {
            result = jdbctemple.update(sql.toString(),queryList.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int deleteGoods(int goodsId) {

        StringBuffer sql = new StringBuffer("DELETE FROM uv_goods_info WHERE id = ?");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(goodsId);

        int result = 0;
        try {
            result = jdbctemple.update(sql.toString(),queryList.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
}
