package com.education.uv.Dao;

import java.util.List;
import java.util.Map;

public interface OrderForGoodsDao
{
    //提交订单
    int commitOrder(Map<String, Object> parameter);

    //统计订单
    List<Map<String, Object>> statisticsOrder(Map<String, Object> parameter);

    //获取个人订单列表
    List<Map<String, Object>> getPersonalOrderList(Map<String, Object> parameter);

    //确认提交订单
    int confirmPayOrder(Map<String, Object> parameter);
}
