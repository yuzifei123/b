package com.education.uv.Service;

import java.util.List;
import java.util.Map;

public interface OtherRequestService
{

    //获取年级列表
    List<Map<String, Object>> getGradeList();

    //获取科目列表
    List<Map<String, Object>> getSubjectList();


}
