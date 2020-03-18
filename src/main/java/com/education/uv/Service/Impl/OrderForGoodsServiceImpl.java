package com.education.uv.Service.Impl;

import com.education.uv.Dao.OrderForGoodsDao;
import com.education.uv.Service.OrderForGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderForGoodsServiceImpl implements OrderForGoodsService {

    @Autowired
    private OrderForGoodsDao orderForGoodsDao;

    @Override
    public int commitOrder(Map<String, Object> parameter) {
        return orderForGoodsDao.commitOrder(parameter);
    }

    @Override
    public List<Map<String, Object>> statisticsOrder(Map<String, Object> parameter) {
        return orderForGoodsDao.statisticsOrder(parameter);
    }

    @Override
    public List<Map<String, Object>> getPersonalOrderList(Map<String, Object> parameter) {
        return orderForGoodsDao.getPersonalOrderList(parameter);
    }

    @Override
    public int confirmPayOrder(Map<String, Object> parameter) {
        return orderForGoodsDao.confirmPayOrder(parameter);
    }
}
