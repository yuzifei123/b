package com.education.uv.Service;

import java.util.List;
import java.util.Map;

public interface HomeworkService {
    //获取所有作业 带分页
    List<Map<String, Object>> getHomeworkList(int pageSize , int page , Map<String,Object> searchMap);

    //按条件获取作业总数
    String getHomeworkTotal(Map<String,Object> searchMap);

    //获取作业详情
    List<Map<String, Object>> getHomeworkDetails(String homeworkId);

    //添加作业
    int insertHomework(Map<String, Object> parameter);

    //修改作业
    int updateHomework(Map<String, Object> parameter);

    //删除作业
    int deleteHomework(String homeworkId);
}
