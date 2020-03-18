package com.education.uv.Controller;

import com.education.uv.Dao.OrderForGoodsDao;
import com.education.uv.Model.ResultData;
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
@RequestMapping("order/")
public class OrderForGoodsController
{
    @Autowired
    OrderForGoodsDao orderForGoodsDao;
    /**
     * 提交订单
     * @return
     */
    @RequestMapping(value = "commitOrder",method = RequestMethod.POST)
    public ResultData commitOrder(HttpServletRequest request, @RequestBody Map<String, Object> parameter)   {
        ResultData result = new ResultData();
        int res = 0;
        try {
            res = orderForGoodsDao.commitOrder(parameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setData(res);
        return result;
    }


    /**
     * 按时间统计 订单
     * @return
     */
    @RequestMapping(value = "statisticsOrder",method = RequestMethod.POST)
    public ResultData statisticsOrder(HttpServletRequest request, @RequestBody Map<String, Object> parameter)   {
        ResultData result = new ResultData();
        List<Map<String, Object>> res = null;
        try {
            res = orderForGoodsDao.statisticsOrder(parameter);
            result.setData(res);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("获取失败");
        }
        return result;
    }


    /**
     * 获取个人订单列表
     * @return
     */
    @RequestMapping(value = "getPersonalOrderList",method = RequestMethod.POST)
    public ResultData getPersonalOrderList(HttpServletRequest request, @RequestBody Map<String, Object> parameter)   {
        ResultData result = new ResultData();
        List<Map<String, Object>> res = null;
        try {
            res = orderForGoodsDao.getPersonalOrderList(parameter);
            result.setData(res);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("获取失败");
        }
        return result;
    }


    /**
     * 标记订单已支付
     * @return
     */
    @RequestMapping(value = "confirmPayOrder",method = RequestMethod.POST)
    public ResultData confirmPayOrder(HttpServletRequest request, @RequestBody Map<String, Object> parameter)   {
        ResultData result = new ResultData();
        int res = 0;
        try {
            res = orderForGoodsDao.confirmPayOrder(parameter);
            result.setData(res);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("获取失败");
        }
        return result;
    }
}
