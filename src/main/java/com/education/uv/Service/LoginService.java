package com.education.uv.Service;

import java.util.List;
import java.util.Map;

public interface LoginService {

    //注册
    int registeUser(Map<String, Object> parameter);

    //登录
    List<Map<String, Object>> loginUser(Map<String, Object> parameter);

    //更改密码
    int changePassword(Map<String, Object> parameter);
}
