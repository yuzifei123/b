package com.education.uv.Controller;

import com.education.uv.Model.ResultData;
import com.education.uv.Service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableAutoConfiguration
@RestController
@RequestMapping("section/")
public class SectionCotroller {

    @Autowired
    SectionService sectionService;

    /**
     * 获取所有章节
     * @return
     */
    @RequestMapping(value = "getSectionList",method = RequestMethod.POST)
    public ResultData getSectionListByPage(HttpServletRequest request, @RequestBody Map<String, Object> parameter )   {
        Map<String, Object> searchMap = (Map<String, Object>) parameter.get("searchMap");
        int page = Integer.valueOf(parameter.get("page").toString());
        int pageSize = Integer.valueOf(parameter.get("pageSize").toString());
        ResultData result = new ResultData();
        List<Map<String, Object>> res = sectionService.getSectionList(pageSize,page,searchMap);
        //获取学生总数
        String total = sectionService.getSectionTotal(searchMap);
            result.setData(res);
        result.setTotal(Integer.parseInt(total));
        return result;
    }

    /**
     * 获取章节详情
     * @return
     */
    @RequestMapping(value = "getSectionDetails",method = RequestMethod.GET)
    public ResultData getSectionDetails(HttpServletRequest request, @RequestParam(value = "sectionId") String SectionId)   {

        List<Map<String, Object>> list = sectionService.getSectionDetails(SectionId);
        ResultData result = new ResultData();
        result.setData(list);
        return result;
    }

    /**
     * 添加章节
     * @return
     */
    @RequestMapping(value = "insertSection",method = RequestMethod.POST)
    public ResultData insertSection(HttpServletRequest request, @RequestBody Map<String, Object> parameter )   {
        Map<String, Object> map = new HashMap<String, Object>();
        ResultData result = new ResultData();
        //上传图片
        try {
            int res = sectionService.insertSection(parameter);
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
     * 修改章节
     * @return
     */
    @RequestMapping(value = "updateSection",method = RequestMethod.POST)
    public ResultData updateSection(HttpServletRequest request, @RequestBody Map<String, Object> parameter)   {
        Map<String, Object> map = new HashMap<String, Object>();
        ResultData result = new ResultData();

        //上传图片
        try {
            int res = sectionService.updateSection(parameter);
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
     * 删除章节
     * @return
     */
    @RequestMapping(value = "deleteSection",method = RequestMethod.POST)
    public ResultData deleteSection(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        ResultData result = new ResultData();
        if (!map.containsKey("sectionId") && map.get("sectionId") == null) {
            result.setMessage("章节id不能为空");
            return result;
        }
        String SectionId = map.get("sectionId").toString();
        int res = 0;
        try {
            res = sectionService.deleteSection(SectionId);
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
