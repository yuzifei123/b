package com.education.uv.Dao;

import java.util.List;
import java.util.Map;

public interface TypeInDao {

    /**
     * 查找所有商品
     *
     * @return
     */
    List<Map<String, Object>> getGoodsList(int isOn);

    //添加商品
    int insertGoods(Map<String, Object> parameter);

    //修改商品
    int updateGoods(Map<String, Object> parameter);

    //删除商品
    int deleteGoods(int goodsId);
}
