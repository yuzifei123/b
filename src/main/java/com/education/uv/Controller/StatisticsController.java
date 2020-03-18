package com.education.uv.Controller;

import com.education.uv.Model.ResultData;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@EnableAutoConfiguration
@RestController
@RequestMapping("/")
public class StatisticsController {
    /**
     * 注册用户
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResultData registeUser(HttpServletRequest request)   {
        ResultData result = new ResultData();
        result.setMessage("test");
        return result;
    }
}
