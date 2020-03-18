package com.education.uv.Dao.Impl;

import com.education.uv.Dao.SectionDao;
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
public class SectionDaoImpl implements SectionDao{

    @Resource
    JdbcTemplate jdbctemple;

    @Override
    public List<Map<String, Object>> getSectionList(int pageSize, int page, Map<String, Object> searchMap) {
        page = page==0 ? 1:page;
        pageSize = pageSize == 0 ? 10 : pageSize;
        StringBuffer sql = new StringBuffer("SELECT sec.id AS sectionId, sec.name, g.name AS grade, su.name AS subject FROM uv_section sec " +
                "LEFT JOIN uv_grade g ON sec.gradeId = g.id  " +
                "LEFT JOIN uv_subject su ON su.id = sec.subjectId " +
                "LIMIT ?,?");
        List<Object> query = new ArrayList<Object>();
        query.add(pageSize *(page - 1));
        query.add(pageSize);

        List<Map<String, Object>> data = jdbctemple.queryForList(sql.toString(),query.toArray());
        return data;
    }

    @Override
    public String getSectionTotal(Map<String, Object> searchMap) {
        String countSql = "SELECT COUNT(*) AS total  FROM uv_section";
        Map<String, Object> map = jdbctemple.queryForMap(countSql);
        return map.get("total").toString();
    }

    @Override
    public List<Map<String, Object>> getSectionDetails(String sectionId) {
        StringBuffer sql = new StringBuffer("SELECT sec.id AS sectionId, sec.name, g.name AS gradeId, su.name AS subjectId ,sec.remark FROM uv_section sec " +
                "LEFT JOIN uv_grade g ON sec.gradeId = g.id " +
                "LEFT JOIN uv_subject su ON su.id = sec.subjectId " +
                "WHERE sec.id = ?");
        List<Object> studetnQuery = new ArrayList<Object>();
        studetnQuery.add(sectionId);

        List<Map<String, Object>> data = jdbctemple.queryForList(sql.toString(),studetnQuery.toArray());
        return data;
    }

    @Override
    public int insertSection(Map<String, Object> request) {

        String remark = request.get("remark").toString();
        String gradeId = request.get("gradeId").toString();
        String name = request.get("name").toString();
        String subjectId = request.get("subjectId").toString();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

        String sql = "INSERT INTO uv_section" +
                " (gradeId,subjectId,name,createTime,remark) " +
                "VALUES(?,?,?,?,?)";
        List queryList = new ArrayList();
        queryList.add(gradeId);
        queryList.add(subjectId);
        queryList.add(name);
        queryList.add(df.format(new Date()));
        queryList.add(remark);
        int res = 0;
        try {
            res = jdbctemple.update(sql,queryList.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public int updateSection(Map<String, Object> request) {
        String remark = request.get("remark").toString();
        String gradeId = request.get("gradeId").toString();
        String name = request.get("name").toString();
        String subjectId = request.get("subjectId").toString();
        String sectionId = request.get("sectionId").toString();

        String sql = "UPDATE uv_section h SET h.name =? , h.remark = ?, h.subjectId = ?, h.gradeId = ? " +
                "WHERE id = ?";
        List queryList = new ArrayList();
        queryList.add(name);
        queryList.add(remark);
        queryList.add(subjectId);
        queryList.add(gradeId);
        queryList.add(sectionId);

        int res = 0;
        try {
            res = jdbctemple.update(sql,queryList.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public int deleteSection(String sectionId) {
        String sql = "DELETE FROM uv_section WHERE id = ?";

        List<Object> queryList = new ArrayList<Object>();
        queryList.add(sectionId);

        int result = 0;
        try {
            result = jdbctemple.update(sql.toString(),queryList.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
}
