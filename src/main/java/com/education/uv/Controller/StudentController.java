package com.education.uv.Controller;

import com.education.uv.Model.ResultData;
import com.education.uv.Service.StudentService;
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
@RequestMapping("student/")
public class StudentController {

    @Autowired
    private StudentService  studentService;
    /**
     * 获取所有学生
     * @return
     */
    @RequestMapping(value = "getStudentList",method = RequestMethod.POST)
    public ResultData getStudentList(HttpServletRequest request, @RequestBody Map<String, Object> parameter )   {
        Map<String, Object> searchMap = (Map<String, Object>) parameter.get("searchMap");
        String page = String.valueOf(parameter.get("page"));
        String pageSize = String.valueOf(parameter.get("pageSize"));
        ResultData result = new ResultData();
        List<Map<String, Object>> res = studentService.getStudentList(pageSize+"",
                page+"", searchMap);
        result.setData(res);
        return result;
    }

    /**
     * 获取所有学生 带分页
     * @return
     */
    @RequestMapping(value = "getStudentListByPage",method = RequestMethod.POST)
    public ResultData getStudentListByPage(HttpServletRequest request, @RequestBody Map<String, Object> parameter )   {
        Map<String, Object> searchMap = (Map<String, Object>) parameter.get("searchMap");
        int page = Integer.valueOf(parameter.get("page").toString());
        int pageSize = Integer.valueOf(parameter.get("pageSize").toString());
        ResultData result = new ResultData();
        List<Map<String, Object>> res = studentService.getStudentListByPage(pageSize,page,searchMap);
        //获取学生总数
        String total = studentService.getStudentTotal(searchMap);
        result.setData(res);
        result.setTotal(Integer.parseInt(total));
        return result;
    }

    /**
     * 获取学生详情
     * @return
     */
    @RequestMapping(value = "getStudentDetails",method = RequestMethod.GET)
    public ResultData getStudentDetails(HttpServletRequest request, @RequestParam(value = "studentId") String studentId)   {

        List<Map<String, Object>> list = studentService.getStudentDetails(studentId);
        ResultData result = new ResultData();
        result.setData(list);
        return result;
    }

    /**
     * 添加学生
     * @return
     */
    @RequestMapping(value = "insertStudent",method = RequestMethod.POST)
    public ResultData insertStudent(HttpServletRequest request)   {
        Map<String, Object> map = new HashMap<String, Object>();
        ResultData result = new ResultData();
        map.put("name",request.getParameter("name"));
        //上传图片
        try {
            if (request instanceof MultipartHttpServletRequest) {
                MultipartFile file = ((MultipartHttpServletRequest) request).getFiles("file").get(0);
                QiNiuManager manager = new QiNiuManager();
                String url = manager.updateImage(file,"uv-icon");
                map.put("icon",url);
                result.setResult(url);
            }
            map.put("name",request.getParameter("name"));
            map.put("birthday",request.getParameter("birthday"));
            map.put("age",request.getParameter("age"));
            map.put("gender",request.getParameter("gender"));
            map.put("school",request.getParameter("school"));
            map.put("classRoom",request.getParameter("classRoom"));
            map.put("phone",request.getParameter("phone"));
            map.put("isOfferFood",request.getParameter("isOfferFood"));
            map.put("remark",request.getParameter("remark"));
            map.put("isOurStudent",request.getParameter("isOurStudent"));


            int res = studentService.insertStudent(map);
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
     * 修改学生
     * @return
     */
    @RequestMapping(value = "updateStudent",method = RequestMethod.POST)
    public ResultData updateStudent(HttpServletRequest request)   {
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

            map.put("name",request.getParameter("name"));
            map.put("birthday",request.getParameter("birthday"));
            map.put("age",request.getParameter("age"));
            map.put("gender",request.getParameter("gender"));
            map.put("school",request.getParameter("school"));
            map.put("classRoom",request.getParameter("classRoom"));
            map.put("phone",request.getParameter("phone"));
            map.put("isOfferFood",request.getParameter("isOfferFood"));
            map.put("remark",request.getParameter("remark"));
            map.put("isOurStudent",request.getParameter("isOurStudent"));
            map.put("studentId",request.getParameter("studentId"));

            int res = studentService.updateStudent(map);
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
     * 删除学生
     * @return
     */
    @RequestMapping(value = "deleteStudent",method = RequestMethod.POST)
    public ResultData deleteStudent(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        ResultData result = new ResultData();
        if (!map.containsKey("studentId") && map.get("studentId") == null) {
            result.setMessage("学生id不能为空");
            return result;
        }
        String studentId = map.get("studentId").toString();
        int res = 0;
        try {
            res = studentService.deleteStudent(studentId);
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
