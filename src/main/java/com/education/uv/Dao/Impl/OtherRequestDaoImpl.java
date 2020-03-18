package com.education.uv.Dao.Impl;

import com.education.uv.Dao.OtherRequestDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Repository
public class OtherRequestDaoImpl implements OtherRequestDao {
    @Resource
    JdbcTemplate jdbctemple;

    @Override
    public List<Map<String, Object>> getGradeList() {
        String sql = "SELECT id , name FROM uv_grade";
        List<Map<String, Object>> list = jdbctemple.queryForList(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> getSubjectList() {
        String sql = "SELECT id , name FROM uv_subject";
        List<Map<String, Object>> list = jdbctemple.queryForList(sql);
        return list;
    }
}
