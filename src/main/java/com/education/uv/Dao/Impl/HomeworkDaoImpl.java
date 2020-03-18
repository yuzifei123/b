package com.education.uv.Dao.Impl;

import com.education.uv.Dao.HomeworkDao;
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
public class HomeworkDaoImpl implements HomeworkDao{


    @Resource
    JdbcTemplate jdbctemple;

    @Override
    public List<Map<String, Object>> getHomeworkList(int pageSize, int page, Map<String, Object> searchMap) {
        page = page==0 ? 1:page;
        pageSize = pageSize == 0 ? 10 : pageSize;
        StringBuffer sql = new StringBuffer("SELECT h.id AS homeworkId, l.username,h.title,h.gradeId,h.subjectId ," +
                "date_format( h.createTime, '%Y-%m-%d %H:%i:%s' ) createTime,h.answerCount,h.wrongCount " +
                "  FROM uv_homework h LEFT JOIN uv_login l ON h.createPerson = l.id  LIMIT ?,?");
        List<Object> query = new ArrayList<Object>();
        query.add(pageSize *(page - 1));
        query.add(pageSize);

        List<Map<String, Object>> data = jdbctemple.queryForList(sql.toString(),query.toArray());
        return data;
    }

    @Override
    public String getHomeworkTotal(Map<String, Object> searchMap) {
        String countSql = "SELECT COUNT(*) AS total  FROM uv_homework";
        Map<String, Object> map = jdbctemple.queryForMap(countSql);
        return map.get("total").toString();
    }

    @Override
    public List<Map<String, Object>> getHomeworkDetails(String homeworkId) {
        StringBuffer sql = new StringBuffer("SELECT h.id AS homeworkId, h.createPerson,g.name AS gradeId,s.name AS subjectId,h.title,h.A,h.B,h.C,h.D,h.answer,h.analysis,h.remark FROM uv_homework h " +
                "LEFT JOIN  uv_login l ON l.id = h.createPerson " +
                "LEFT JOIN uv_subject s ON s.id = h.subjectId " +
                "LEFT JOIN uv_grade g ON g.id = h.gradeId " +
                "WHERE h.id = ?");
        List<Object> studetnQuery = new ArrayList<Object>();
        studetnQuery.add(homeworkId);

        List<Map<String, Object>> data = jdbctemple.queryForList(sql.toString(),studetnQuery.toArray());
        return data;
    }

    @Override
    public int insertHomework(Map<String, Object> request) {
        String title = request.get("title").toString();
        String a = request.get("A").toString();
        String b = request.get("B").toString();
        String c = request.get("C").toString();
        String d = request.get("D").toString();
        String createPerson = request.get("createPerson").toString();
        String remark = request.get("remark").toString();
        String gradeId = request.get("gradeId").toString();
        String analysis = request.get("analysis").toString();
        String subjectId = request.get("subjectId").toString();
        String answer = request.get("answer").toString();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

        String sql = "INSERT INTO uv_homework" +
                " (gradeId,subjectId,title,A,B,C,D,answer,analysis,createTime,remark,createPerson) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        List queryList = new ArrayList();
        queryList.add(gradeId);
        queryList.add(subjectId);
        queryList.add(title);
        queryList.add(a);
        queryList.add(b);
        queryList.add(c);
        queryList.add(d);
        queryList.add(answer);
        queryList.add(analysis);
        queryList.add(df.format(new Date()));
        queryList.add(remark);
        queryList.add(createPerson);
        int res = 0;
        try {
            res = jdbctemple.update(sql,queryList.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public int updateHomework(Map<String, Object> request) {
        String title = request.get("title").toString();
        String a = request.get("A").toString();
        String b = request.get("B").toString();
        String c = request.get("C").toString();
        String d = request.get("D").toString();
        String remark = request.get("remark").toString();
        String gradeId = request.get("gradeId").toString();
        String analysis = request.get("analysis").toString();
        String subjectId = request.get("subjectId").toString();
        String answer = request.get("answer").toString();
                String homeworkId = request.get("homeworkId").toString();


        String sql = "UPDATE uv_homework h SET h.title =? ,h.A = ?,h.B = ?," +
                " h.C = ?, h.D = ?,h.answer = ?, h.analysis = ?, h.remark = ?, h.subjectId = ?, h.gradeId = ? " +
                "WHERE id = ?";
        List queryList = new ArrayList();

        queryList.add(title);
        queryList.add(a);
        queryList.add(b);
        queryList.add(c);
        queryList.add(d);
        queryList.add(answer);
        queryList.add(analysis);
        queryList.add(remark);
        queryList.add(subjectId);
        queryList.add(gradeId);
        queryList.add(homeworkId);

        int res = 0;
        try {
            res = jdbctemple.update(sql,queryList.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public int deleteHomework(String homeworkId) {
        String sql = "DELETE FROM uv_homework WHERE id = ?";

        List<Object> queryList = new ArrayList<Object>();
        queryList.add(homeworkId);

        int result = 0;
        try {
            result = jdbctemple.update(sql.toString(),queryList.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
}
