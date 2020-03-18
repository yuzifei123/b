package com.education.uv.Service;

import java.util.List;
import java.util.Map;

public interface StudentService
{

    //获取所有学生
//    List<Map<String, Object>> getStudentList(int isOn);
    List<Map<String, Object>> getStudentList(String pageSize , String page , Map<String,Object> searchMap);

    //获取所有学生 带分页
    List<Map<String, Object>> getStudentListByPage(int pageSize ,int page ,Map<String,Object> searchMap);

    //按条件获取学生总数
    String getStudentTotal(Map<String,Object> searchMap);

    //获取学生详情
    List<Map<String, Object>> getStudentDetails(String studentId);

    //添加学生
    int insertStudent(Map<String, Object> parameter);

    //修改学生
    int updateStudent(Map<String, Object> parameter);

    //删除学生
    int deleteStudent(String studentId);
}
