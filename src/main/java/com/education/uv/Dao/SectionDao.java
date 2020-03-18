package com.education.uv.Dao;

import java.util.List;
import java.util.Map;

public interface SectionDao
{
    //获取所有章节 带分页
    List<Map<String, Object>> getSectionList(int pageSize , int page , Map<String,Object> searchMap);

    //按条件获取章节总数
    String getSectionTotal(Map<String,Object> searchMap);

    //获取章节详情
    List<Map<String, Object>> getSectionDetails(String sectionId);

    //添加章节
    int insertSection(Map<String, Object> parameter);

    //修改作业
    int updateSection(Map<String, Object> parameter);

    //删除章节
    int deleteSection(String sectionId);
}
