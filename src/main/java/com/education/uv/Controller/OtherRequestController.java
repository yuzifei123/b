package com.education.uv.Controller;

import com.education.uv.Model.ResultData;
import com.education.uv.Service.OtherRequestService;
import com.education.uv.Utile.QiNiuManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@EnableAutoConfiguration
@RestController
@RequestMapping("other/")
public class OtherRequestController
{
    @Autowired
    OtherRequestService otherRequestService;

    /**
     * 获取年级列表
     * @return
     */
    @RequestMapping(value = "getGradeList",method = RequestMethod.GET)
    public ResultData getGradeList(HttpServletRequest request )   {
        ResultData result = new ResultData();
        List<Map<String, Object>> res = otherRequestService.getGradeList();
        result.setData(res);
        return result;
    }

    /**
     * 获取年级列表
     * @return
     */
    @RequestMapping(value = "getSubjectList",method = RequestMethod.GET)
    public ResultData getSubjectList(HttpServletRequest request )   {
        ResultData result = new ResultData();
        List<Map<String, Object>> res = otherRequestService.getSubjectList();
        result.setData(res);
        return result;
    }

    /**
     * 上传文件
     * @return
     */
    @RequestMapping(value = "uploadAttachment",method = RequestMethod.POST)
    public ResultData uploadAttachment(HttpServletRequest request)   {

        ResultData result = new ResultData();
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, String[]> map1 = request.getParameterMap();
        MultipartFile file = ((MultipartHttpServletRequest) request).getFiles("file").get(0);
//
        System.out.println(request);
//        try {
//            MultipartFile file = ((MultipartHttpServletRequest) request).getFiles("file").get(0);
//
//            if (request instanceof MultipartHttpServletRequest) {
//
//            }
//
////            if (res != 0) {
//
//                result.setMessage("添加成功");
//                return result;
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            result.setMessage("添加失败");
//        }
        return result;
    }


    @RequestMapping(value = "loginUser",method = RequestMethod.POST)
    public String loginUser(HttpServletRequest request, @RequestBody Map<String, Object> parameter){
        System.out.println(parameter);
        return "123";
    }
}
