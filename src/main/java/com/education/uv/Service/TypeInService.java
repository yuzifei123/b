package com.education.uv.Service;

import java.util.List;
import java.util.Map;

public interface TypeInService {

    /**
     * 查找商品列表
     *
     * @return
     */
    List<Map<String, Object>> getGoodsList(int isOn) throws Exception;

    //添加商品
    int insertGoods(Map<String, Object> parameter);

    //修改商品
    int updateGoods(Map<String, Object> parameter);

    //删除商品
    int deleteGoods(int goodsId);
}
