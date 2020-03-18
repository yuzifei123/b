package com.education.uv.Service.Impl;

import com.education.uv.Dao.HomeworkDao;
import com.education.uv.Service.HomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HomeworkServiceImpl implements HomeworkService{

    @Autowired
    HomeworkDao homeworkDao;


    @Override
    public List<Map<String, Object>> getHomeworkList(int pageSize, int page, Map<String, Object> searchMap) {
        return homeworkDao.getHomeworkList(pageSize,page,searchMap);
    }

    @Override
    public String getHomeworkTotal(Map<String, Object> searchMap) {
        return homeworkDao.getHomeworkTotal(searchMap);
    }

    @Override
    public List<Map<String, Object>> getHomeworkDetails(String homeworkId) {
        return homeworkDao.getHomeworkDetails(homeworkId);
    }

    @Override
    public int insertHomework(Map<String, Object> parameter) {
        return homeworkDao.insertHomework(parameter);
    }

    @Override
    public int updateHomework(Map<String, Object> parameter) {
        return homeworkDao.updateHomework(parameter);
    }

    @Override
    public int deleteHomework(String homeworkId) {
        return homeworkDao.deleteHomework(homeworkId);
    }
}
