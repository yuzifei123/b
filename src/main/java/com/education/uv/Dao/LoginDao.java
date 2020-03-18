package com.education.uv.Dao;

import java.util.List;
import java.util.Map;

public interface LoginDao {

    //注册
    int registeUser(Map<String, Object> parameter);

    //登录
    List<Map<String, Object>> loginUser(Map<String, Object> parameter);

    //修改密码
    int changePassword(Map<String, Object> parameter);
}
