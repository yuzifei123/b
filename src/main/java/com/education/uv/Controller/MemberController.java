package com.education.uv.Controller;

import com.education.uv.Model.ResultData;
import com.education.uv.Service.MemberService;
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
@RequestMapping("member/")
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * 获取所有员工
     * @return
     */
    @RequestMapping(value = "getMemberList",method = RequestMethod.POST)
    public ResultData getMemberListByPage(HttpServletRequest request, @RequestBody Map<String, Object> parameter )   {
        Map<String, Object> searchMap = (Map<String, Object>) parameter.get("searchMap");
        int page = Integer.valueOf(parameter.get("page").toString());
        int pageSize = Integer.valueOf(parameter.get("pageSize").toString());
        ResultData result = new ResultData();
        List<Map<String, Object>> res = memberService.getMemberList(pageSize,page,searchMap);
        //获取学生总数
        String total = memberService.getMemberTotal(searchMap);
        result.setData(res);
        result.setTotal(Integer.parseInt(total));
        return result;
    }

    /**
     * 获取员工详情
     * @return
     */
    @RequestMapping(value = "getMemberDetails",method = RequestMethod.GET)
    public ResultData getMemberDetails(HttpServletRequest request, @RequestParam(value = "memberId") String memberId)   {

        List<Map<String, Object>> list = memberService.getMemberDetails(memberId);
        ResultData result = new ResultData();
        result.setData(list);
        return result;
    }

    /**
     * 添加员工
     * @return
     */
    @RequestMapping(value = "insertMember",method = RequestMethod.POST)
    public ResultData insertMember(HttpServletRequest request)   {
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
            map.put("employTime",request.getParameter("employTime"));
            map.put("age",request.getParameter("age"));
            map.put("gender",request.getParameter("gender"));
            map.put("phone",request.getParameter("phone"));
            map.put("remark",request.getParameter("remark"));
            map.put("isOurMember",request.getParameter("isOurMember"));
            map.put("school",request.getParameter("school"));
            map.put("isPartJob",request.getParameter("isPartJob"));
            map.put("salary",request.getParameter("salary"));
            map.put("education",request.getParameter("education"));

            int res = memberService.insertMember(map);
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
     * 修改员工
     * @return
     */
    @RequestMapping(value = "updateMember",method = RequestMethod.POST)
    public ResultData updateMember(HttpServletRequest request)   {
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

            map.put("phone",request.getParameter("phone"));
            map.put("remark",request.getParameter("remark"));
            map.put("isOurMember",request.getParameter("isOurMember"));
            map.put("memberId",request.getParameter("memberId"));

            int res = memberService.updateMember(map);
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
     * 删除员工
     * @return
     */
    @RequestMapping(value = "deleteMember",method = RequestMethod.POST)
    public ResultData deleteMember(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        ResultData result = new ResultData();
        if (!map.containsKey("memberId") && map.get("memberId") == null) {
            result.setMessage("员工id不能为空");
            return result;
        }
        String memberId = map.get("memberId").toString();
        int res = 0;
        try {
            res = memberService.deleteMember(memberId);
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
