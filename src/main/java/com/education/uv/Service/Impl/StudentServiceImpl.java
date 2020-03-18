package com.education.uv.Service.Impl;

import com.education.uv.Dao.StudentDao;
import com.education.uv.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentDao studentDao;

//    @Override
//    public List<Map<String, Object>> getStudentList(int isOn) {
//        return studentDao.getStudentList(isOn);
//    }

    @Override
    public List<Map<String, Object>> getStudentListByPage(int pageSize, int page, Map<String, Object> searchMap) {
        return studentDao.getStudentListByPage(pageSize,page,searchMap);
    }

    @Override
    public String getStudentTotal(Map<String, Object> searchMap) {
        return studentDao.getStudentTotal(searchMap);
    }

    @Override
    public List<Map<String, Object>> getStudentDetails(String studentId) {
        return studentDao.getStudentDetails(studentId);
    }

    @Override
    public int insertStudent(Map<String, Object> parameter) {
        return studentDao.insertStudent(parameter);
    }

    @Override
    public int updateStudent(Map<String, Object> parameter) {
        return studentDao.updateStudent(parameter);
    }

    @Override
    public int deleteStudent(String studentId) {
        return studentDao.deleteStudent(studentId);
    }

    @Override
    public List<Map<String, Object>> getStudentList(String pageSize, String page, Map searchMap) {
        return studentDao.getStudentList(pageSize,page,searchMap);
    }


}
