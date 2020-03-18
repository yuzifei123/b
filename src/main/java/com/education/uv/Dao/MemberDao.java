package com.education.uv.Dao;

import java.util.List;
import java.util.Map;

public interface MemberDao {
    //获取所有员工 带分页
    List<Map<String, Object>> getMemberList(int pageSize , int page , Map<String,Object> searchMap);

    //按条件获取员工总数
    String getMemberTotal(Map<String,Object> searchMap);

    //获取员工详情
    List<Map<String, Object>> getMemberDetails(String studentId);

    //添加员工
    int insertMember(Map<String, Object> parameter);

    //修改员工
    int updateMember(Map<String, Object> parameter);

    //删除员工
    int deleteMember(String studentId);
}
