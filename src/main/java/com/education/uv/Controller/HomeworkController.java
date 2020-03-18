package com.education.uv.Controller;

import com.education.uv.Model.ResultData;
import com.education.uv.Service.HomeworkService;
import com.education.uv.Utile.QiNiuManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableAutoConfiguration
@RestController
@RequestMapping("homework/")
public class HomeworkController {

    @Autowired
    HomeworkService homeworkService;

    /**
     * 获取所有作业
     * @return
     */
    @RequestMapping(value = "getHomeworkList",method = RequestMethod.POST)
    public ResultData getHomeworkListByPage(HttpServletRequest request, @RequestBody Map<String, Object> parameter )   {
        Map<String, Object> searchMap = (Map<String, Object>) parameter.get("searchMap");
        int page = Integer.valueOf(parameter.get("page").toString());
        int pageSize = Integer.valueOf(parameter.get("pageSize").toString());
        ResultData result = new ResultData();
        List<Map<String, Object>> res = homeworkService.getHomeworkList(pageSize,page,searchMap);
        //获取学生总数
        String total = homeworkService.getHomeworkTotal(searchMap);
        result.setData(res);
        result.setTotal(Integer.parseInt(total));
        return result;
    }

    /**
     * 获取作业详情
     * @return
     */
    @RequestMapping(value = "getHomeworkDetails",method = RequestMethod.GET)
    public ResultData getHomeworkDetails(HttpServletRequest request, @RequestParam(value = "homeworkId") String homeworkId)   {

        List<Map<String, Object>> list = homeworkService.getHomeworkDetails(homeworkId);
        ResultData result = new ResultData();
        result.setData(list);
        return result;
    }

    /**
     * 添加作业
     * @return
     */
    @RequestMapping(value = "insertHomework",method = RequestMethod.POST)
    public ResultData insertHomework(HttpServletRequest request, @RequestBody Map<String, Object> parameter )   {
        Map<String, Object> map = new HashMap<String, Object>();
        ResultData result = new ResultData();
        //上传图片
        try {
            if (request instanceof MultipartHttpServletRequest) {
                MultipartFile file = ((MultipartHttpServletRequest) request).getFiles("file").get(0);
                QiNiuManager manager = new QiNiuManager();
                String url = manager.updateImage(file,"uv-icon");
                map.put("icon",url);
                result.setResult(url);
            }
//            map.put("title",request.getParameter("title"));
//            map.put("A",request.getParameter("A"));
//            map.put("B",request.getParameter("B"));
//            map.put("C",request.getParameter("C"));
//            map.put("D",request.getParameter("D"));
//            map.put("createPerson",request.getParameter("createPerson"));
//            map.put("remark",request.getParameter("remark"));
//            map.put("gradeId",request.getParameter("gradeId"));
//            map.put("subjectId",request.getParameter("subjectId"));
//            map.put("analysis",request.getParameter("analysis"));
//            map.put("answer",request.getParameter("answer"));

            int res = homeworkService.insertHomework(parameter);
            if (res != 0) {

                result.setMessage("添加成功");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("添加失败");
        }
        return result;

    }


    /**
     * 修改作业
     * @return
     */
    @RequestMapping(value = "updateHomework",method = RequestMethod.POST)
    public ResultData updateHomework(HttpServletRequest request, @RequestBody Map<String, Object> parameter)   {
        Map<String, Object> map = new HashMap<String, Object>();
        ResultData result = new ResultData();

        //上传图片
        try {
            if (request instanceof MultipartHttpServletRequest) {
                MultipartFile file = ((MultipartHttpServletRequest) request).getFiles("file").get(0);
                QiNiuManager manager = new QiNiuManager();
                String url = manager.updateImage(file,"uv-icon");
                map.put("icon",url);
                result.setResult(url);
            }

//            map.put("title",request.getParameter("title"));
//            map.put("A",request.getParameter("A"));
//            map.put("B",request.getParameter("B"));
//            map.put("C",request.getParameter("C"));
//            map.put("D",request.getParameter("D"));
//            map.put("createPerson",request.getParameter("createPerson"));
//            map.put("remark",request.getParameter("remark"));
//            map.put("gradeId",request.getParameter("gradeId"));
//            map.put("subjectId",request.getParameter("subjectId"));
//            map.put("analysis",request.getParameter("analysis"));
//            map.put("answer",request.getParameter("answer"));
//            map.put("homeworkId",request.getParameter("homeworkId"));

            int res = homeworkService.updateHomework(parameter);
            if (res != 0) {
//                result.setResult(url);
                result.setMessage("修改成功");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("修改失败");
        }
        return result;

    }


    /**
     * 删除作业
     * @return
     */
    @RequestMapping(value = "deleteHomework",method = RequestMethod.POST)
    public ResultData deleteHomework(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        ResultData result = new ResultData();
        if (!map.containsKey("homeworkId") && map.get("homeworkId") == null) {
            result.setMessage("作业id不能为空");
            return result;
        }
        String homeworkId = map.get("homeworkId").toString();
        int res = 0;
        try {
            res = homeworkService.deleteHomework(homeworkId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (res != 0) {
            result.setMessage("删除成功");
            return result;
        }
        return result;
    }

}
