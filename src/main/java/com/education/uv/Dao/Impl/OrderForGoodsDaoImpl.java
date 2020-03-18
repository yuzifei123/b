package com.education.uv.Dao.Impl;

import com.education.uv.Dao.OrderForGoodsDao;
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
public class OrderForGoodsDaoImpl implements OrderForGoodsDao {

    @Resource
    JdbcTemplate jdbctemple;

    @Override
    public int commitOrder(Map<String, Object> parameter) {

        List<Map<String, Object>> list = (List<Map<String, Object>>) parameter.get("data");

        String sql = "INSERT  INTO uv_order (studentId,count,goodsId,price,buy_time,timestamp) VALUES (?,?,?,?,?,?)";

        long time = System.currentTimeMillis()/1000;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

        List<Object> query = new ArrayList<Object>();
        int result = 0;
        for (Map<String, Object> map: list ) {
            query.clear();
            String studentId = map.get("studentId").toString();
            String count = map.get("count").toString();
            String goodsId = map.get("goodsId").toString();
            String price = map.get("price").toString();
            query.add(studentId);
            query.add(count);
            query.add(goodsId);
            query.add(price);
            query.add(df.format(new Date()));
            query.add(time);
            try {
                result = jdbctemple.update(sql,query.toArray());
            } catch (DataAccessException e) {
                e.printStackTrace();
                result = 0;
            }
        }


        return result;
    }

    @Override
    public List<Map<String, Object>> statisticsOrder(Map<String, Object> parameter) {

        String start = parameter.get("start").toString();
        String end = parameter.get("end").toString();
        int isAll = Integer.parseInt(parameter.get("isAll").toString());
        String sql = "";
        if (isAll == 1) {//搜索全部订单
            sql = "SELECT o.id,o.count,o.buy_time,o.goodsId,g.name as goodsName," +
                    "o.studentId,p.name as studentName,g.price,o.isCheckOut FROM uv_order o " +
                    "LEFT JOIN uv_person AS p ON o.studentId = p.studentId " +
                    "LEFT JOIN uv_goods_info AS g ON o.goodsId = g.id " +
                    "WHERE o.timestamp > (?) AND o.timestamp < (?) ORDER BY o.timestamp DESC";
        } else  {//搜索整合订单
            sql = "SELECT o.id,o.count,o.buy_time,o.goodsId,g.name as goodsName," +
                    "o.studentId,p.name as studentName,g.price,o.isCheckOut ,SUM(o.count * g.price) as totle FROM uv_order o " +
                    "LEFT JOIN uv_person AS p ON o.studentId = p.studentId " +
                    "LEFT JOIN uv_goods_info AS g ON o.goodsId = g.id " +
                    "WHERE o.timestamp > (?) AND o.timestamp < (?) AND o.isCheckOut = 0 GROUP BY studentId ORDER BY o.timestamp DESC";
        }


        List<Object> query = new ArrayList<Object>();
        query.add(start);
        query.add(end);

        List<Map<String, Object>> res = null;
        try {
            res = jdbctemple.queryForList(sql,query.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Map<String, Object>> getPersonalOrderList(Map<String, Object> parameter) {

        String start = parameter.get("start").toString();
        String end = parameter.get("end").toString();
        int studentId = Integer.parseInt(parameter.get("studentId").toString());

        String sql  = "SELECT o.id,o.count,o.buy_time,o.goodsId,g.name as goodsName," +
                "                    o.studentId,p.name as studentName,g.price,o.isCheckOut FROM uv_order o " +
                "                    LEFT JOIN uv_person AS p ON o.studentId = p.studentId " +
                "                    LEFT JOIN uv_goods_info AS g ON o.goodsId = g.id " +
                "                    WHERE o.timestamp > ? AND o.timestamp < ? AND o.studentId = ? " +
                "ORDER BY o.timestamp DESC";


        List<Object> query = new ArrayList<Object>();
        query.add(start);
        query.add(end);
        query.add(studentId);
        List<Map<String, Object>> res = null;
        try {
            res = jdbctemple.queryForList(sql,query.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public int confirmPayOrder(Map<String, Object> parameter) {
        String ids = parameter.get("ids").toString();
        String sql  = "UPDATE uv_order SET isCheckOut = 1 WHERE id = ?";
        List<Object> query = new ArrayList<Object>();

        String[] arr  = ids.split(",");
        int res = 0;
        for (String id: arr ) {
            query.clear();
            query.add(id);
            try {
                res = jdbctemple.update(sql,query.toArray());
            } catch (DataAccessException e) {
                e.printStackTrace();
                return res;
            }
        }
        return res;
    }
}
