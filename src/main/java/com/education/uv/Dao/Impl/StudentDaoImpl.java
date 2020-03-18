package com.education.uv.Dao.Impl;

import com.education.uv.Dao.StudentDao;
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
public class StudentDaoImpl implements StudentDao {

    @Resource
    JdbcTemplate jdbctemple;


//    @Override
//    public List<Map<String, Object>> getStudentList(int isOn) {
//        StringBuffer sql = new StringBuffer("SELECT p.studentId , p.name,p.phone,s.class,p.icon,s.isOurStudent  FROM uv_student s LEFT JOIN uv_person AS p ON s.id = p.studentId  ");
//        if (isOn == 1) {//1是上架的  0 是所有的
//            sql.append(" WHERE s.isOfferFood = 1");
//        }
//        List<Map<String, Object>> data = jdbctemple.queryForList(sql.toString());
//        return data;
//    }


    @Override
    public List<Map<String, Object>> getStudentList(String pageSize, String page, Map<String, Object> searchMap) {
        int isOn  = Integer.parseInt(searchMap.get("isOn").toString());

        StringBuffer sql = new StringBuffer("SELECT p.studentId , p.name,p.phone,s.class AS classRoom,p.icon,s.isOurStudent  FROM uv_student s LEFT JOIN uv_person AS p ON s.id = p.studentId  ");

        if (isOn == 1) {//1是上架的  0 是所有的
            sql.append(" WHERE s.isOfferFood = 1");
        }
        List<Map<String, Object>> data = jdbctemple.queryForList(sql.toString());
        return data;
    }

    @Override
    public List<Map<String, Object>> getStudentListByPage(int pageSize, int page, Map<String, Object> searchMap) {

        page = page==0 ? 1:page;
        pageSize = pageSize == 0 ? 10 : pageSize;
        StringBuffer sql = new StringBuffer("SELECT p.studentId , p.name,p.phone,s.class AS classRoom,p.icon,s.isOurStudent  FROM uv_student s LEFT JOIN uv_person AS p ON s.id = p.studentId  LIMIT ?,?");
        List<Object> query = new ArrayList<Object>();
        query.add(pageSize *(page - 1));
        query.add(pageSize);

        List<Map<String, Object>> data = jdbctemple.queryForList(sql.toString(),query.toArray());


        return data;
    }

    @Override
    public String getStudentTotal(Map<String, Object> searchMap) {

        String countSql = "SELECT COUNT(*) AS total  FROM uv_student s LEFT JOIN uv_person  p ON s.id = p.studentId";
        Map<String, Object> map = jdbctemple.queryForMap(countSql);

        return map.get("total").toString();
    }

    @Override
    public List<Map<String, Object>> getStudentDetails(String studentId) {
        StringBuffer sql = new StringBuffer("SELECT p.studentId ,p.age,p.icon, p.name,s.class AS classRoom, s.school ,s.isOfferFood,s.isOurStudent ," +
                " p.phone,p.remark,p.birthday,p.gender " +
                "FROM uv_student s " +
                "LEFT JOIN uv_person AS p ON s.id = p.studentId" +
                " WHERE s.id = ?");
        List<Object> studetnQuery = new ArrayList<Object>();
        studetnQuery.add(studentId);

        List<Map<String, Object>> data = jdbctemple.queryForList(sql.toString(),studetnQuery.toArray());
        return data;
    }

    @Transactional
    @Override
    public int insertStudent(Map<String, Object> parameter) {
        String name = parameter.get("name").toString();
        String birthDay = parameter.get("birthday").toString();
        int age  = Integer.parseInt(parameter.get("age").toString());
        int gender = Integer.parseInt(parameter.get("gender").toString());
        String school = parameter.get("school").toString();
        String classRoom = parameter.get("classRoom").toString();
        String phone = parameter.get("phone").toString();
        String icon = "";
        if (parameter.containsKey("icon")) {
            icon = parameter.get("icon").toString();
        }


        int isOfferFood = Integer.parseInt(parameter.get("isOfferFood").toString());
        int isOurStudent = Integer.parseInt(parameter.get("isOurStudent").toString());
        String remark = parameter.get("remark").toString();

        long time = System.currentTimeMillis()/1000;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

        StringBuffer studentSql = new StringBuffer("INSERT INTO uv_student (class , school , isOurStudent,isOfferFood) VALUES (?,?,?,?)");

        List<Object> studetnQuery = new ArrayList<Object>();
        studetnQuery.add(classRoom);
        studetnQuery.add(school);
        studetnQuery.add(isOurStudent);
        studetnQuery.add(isOfferFood);

//        插入到student表中 并且返回主键id
        long generatedId = 0;
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbctemple.update(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                    PreparedStatement ps = connection.prepareStatement(studentSql.toString(),Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, classRoom);
                    ps.setString(2, school);
                    ps.setString(3, isOurStudent+"");
                    ps.setString(4, isOfferFood+"");
                    return ps;
                }
            }, keyHolder);
            generatedId = keyHolder.getKey().longValue();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }


    //插入到person 表中
        StringBuffer personSql = new StringBuffer("INSERT INTO uv_person (name , icon , birthday,phone,age,gender,remark,create_time,timestamp,studentId) VALUES (?,?,?,?,?,?,?,?,?,?)");
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

    @Override
    public int updateStudent(Map<String, Object> parameter) {
        String name = parameter.get("name").toString();
        String birthDay = parameter.get("birthday").toString();
        int age  = Integer.parseInt(parameter.get("age").toString());
        int gender = Integer.parseInt(parameter.get("gender").toString());
        String school = parameter.get("school").toString();
        String classRoom = parameter.get("classRoom").toString();
        String phone = parameter.get("phone").toString();

        String icon = "";
        if (parameter.containsKey("icon")) {
            icon = parameter.get("icon").toString();
        }

        String studentId = parameter.get("studentId").toString();

        int isOfferFood = Integer.parseInt(parameter.get("isOfferFood").toString());
        int isOurStudent = Integer.parseInt(parameter.get("isOurStudent").toString());
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
                " WHERE p.studentId = ?");
        personQuery.add(birthDay);
        personQuery.add(phone);
        personQuery.add(age);
        personQuery.add(gender);
        personQuery.add(remark);
        personQuery.add(studentId);

        StringBuffer studentSql = new StringBuffer("UPDATE uv_student p set p.class = ? ,p.school = ?, " +
                "p.isOurStudent = ? ,p.isOfferFood = ?  " +
                "WHERE p.id = ?");
        List<Object> studetnQuery = new ArrayList<Object>();
        studetnQuery.add(classRoom);
        studetnQuery.add(school);
        studetnQuery.add(isOurStudent);
        studetnQuery.add(isOfferFood);
        studetnQuery.add(studentId);

        int personResult = 0;
        try {
            jdbctemple.update(studentSql.toString(),studetnQuery.toArray());
            personResult = jdbctemple.update(personSql.toString(),personQuery.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return personResult;
    }

    @Override
    public int deleteStudent(String studentId) {

        String deletePerrsonSql = "DELETE FROM uv_person WHERE studentId = ?";

        StringBuffer sql = new StringBuffer("DELETE FROM uv_student WHERE id = ?");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(studentId);

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
