package com.education.uv.Controller;

import com.education.uv.Model.ResultData;
import com.education.uv.Service.TypeInService;
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
@RequestMapping("goods/")
public class TypeInController {

    @Autowired
    private TypeInService typeInService;

    /**
     * 获取所有商品
     * @return
     */
    @RequestMapping(value = "getGoodsList",method = RequestMethod.GET)
    public List<Map<String, Object>> getGoodsList(HttpServletRequest request,@RequestParam(value = "isOn") String isOn) throws Exception {
        return typeInService.getGoodsList(Integer.parseInt(isOn));
    }


    @RequestMapping(value = "updateGoods",method = RequestMethod.POST)
    public ResultData updateGoods(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        ResultData result = new ResultData();
        //上传图片
        try {
            String url = null;
            map.put("name",request.getParameter("name"));
            map.put("isOn",request.getParameter("isOn"));
            map.put("price",request.getParameter("price"));
            map.put("id",request.getParameter("id"));
            String haveImage = request.getParameter("haveImage");
            if ( haveImage != null && haveImage.equals("1") ) {
                MultipartFile file = ((MultipartHttpServletRequest) request).getFiles("file").get(0);
                QiNiuManager manager = new QiNiuManager();
                url = manager.updateImage(file,"uv-image");
                map.put("imageUrl",url);
            }
            int res = typeInService.updateGoods(map);
            if (res != 0) {
                result.setResult(url);
                result.setMessage("修改成功");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("修改失败");
        }
        return result;
    }

    @RequestMapping(value = "insertGoods",method = RequestMethod.POST)
    public ResultData insertGoods(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        ResultData result = new ResultData();

        //上传图片
        try {
            MultipartFile file = ((MultipartHttpServletRequest) request).getFiles("file").get(0);
            QiNiuManager manager = new QiNiuManager();
            String url = manager.updateImage(file,"uv-image");
            map.put("name",request.getParameter("name"));
            map.put("isOn",request.getParameter("isOn"));
            map.put("price",request.getParameter("price"));
            map.put("imageUrl",url);
            int res = typeInService.insertGoods(map);
            if (res != 0) {
                result.setResult(url);
                result.setMessage("添加成功");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("添加失败");
        }
        return result;
    }

    @RequestMapping(value = "deleteGoods",method = RequestMethod.POST)
    public ResultData deleteGoodsById(HttpServletRequest request,@RequestBody Map<String, Object> paraMap) {
        ResultData result = new ResultData();
        try {
            int res =typeInService.deleteGoods(Integer.parseInt(paraMap.get("id").toString()));
            if (res != 0) {
                result.setMessage("删除成功");
            } else {
                result.setMessage("删除失败");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return result;
    }

}
