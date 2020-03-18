package com.education.uv.Service.Impl;

import com.education.uv.Dao.LoginDao;
import com.education.uv.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private LoginDao loginDao;

    @Override
    public int registeUser(Map<String, Object> parameter) {
        return loginDao.registeUser(parameter);
    }

    @Override
    public List<Map<String, Object>> loginUser(Map<String, Object> parameter) {
        return loginDao.loginUser(parameter);
    }

    @Override
    public int changePassword(Map<String, Object> parameter) {
        return loginDao.changePassword(parameter);
    }
}
