package com.education.uv.Service.Impl;

import com.education.uv.Dao.TypeInDao;
import com.education.uv.Service.TypeInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TypeInServiceImpl implements TypeInService{

    @Autowired
    private TypeInDao typeInDao;

    @Override
    public List<Map<String, Object>> getGoodsList(int isOn) throws Exception {
        return typeInDao.getGoodsList(isOn);
    }

    @Override
    public int insertGoods(Map<String, Object> parameter) {
        return typeInDao.insertGoods(parameter);
    }

    @Override
    public int updateGoods(Map<String, Object> parameter) {
        return typeInDao.updateGoods(parameter);
    }

    @Override
    public int deleteGoods(int goodsId) {
        return typeInDao.deleteGoods(goodsId);
    }


}
