package com.education.uv.Controller;

import com.education.uv.Dao.OrderForGoodsDao;
import com.education.uv.Model.ResultData;
import com.education.uv.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@EnableAutoConfiguration
@RestController
@RequestMapping("login/")
public class LoginController {

    @Autowired
    LoginService loginService;
    /**
     * 注册用户
     * @return
     */
    @RequestMapping(value = "registeUser",method = RequestMethod.POST)
    public ResultData registeUser(HttpServletRequest request, @RequestBody Map<String, Object> parameter)   {
        ResultData result = new ResultData();
        int res = 0;
        try {
            res = loginService.registeUser(parameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setData(res);
        return result;
    }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping(value = "changePassword",method = RequestMethod.POST)
    public ResultData changePassword(HttpServletRequest request, @RequestBody Map<String, Object> parameter)   {
        ResultData result = new ResultData();
        int res = 0;
        try {
            res = loginService.changePassword(parameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (res == 0) {
            result.setMessage("修改密码失败");
        } else if (res == 3) {
            result.setMessage("原始密码有错误");
        }  else {
            result.setMessage("修改密码成功");
        }
        result.setFlag(String.valueOf(res));
        return result;
    }

    /**
     * 用户登录
     * @return
     */
    @RequestMapping(value = "loginUser",method = RequestMethod.POST)
    public ResultData loginUser(HttpServletRequest request, @RequestBody Map<String, Object> parameter)   {
        ResultData result = new ResultData();
        List<Map<String, Object>> res = null;
        try {
            res = loginService.loginUser(parameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setData(res);
        if (res.size() == 1 && res.get(0).size() == 1){
            result.setFlag("0");
            result.setMessage(res.get(0).get("message").toString());
        } else if (res.size() == 0) {
            result.setFlag("0");
            result.setMessage("用户不存在");
        } else {
            result.setFlag("1");
            result.setMessage("登录成功");
        }
        return result;
    }

    /**
     * 用户登出
     * @return
     */
    @RequestMapping(value = "logout",method = RequestMethod.POST)
    public ResultData logoutUser(HttpServletRequest request, @RequestBody Map<String, Object> parameter)   {
        ResultData result = new ResultData();
        System.out.println(parameter);
        result.setFlag("1");
        return result;
    }
}
