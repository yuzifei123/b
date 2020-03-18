package com.education.uv.Service.Impl;

import com.education.uv.Dao.OtherRequestDao;
import com.education.uv.Service.OtherRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OtherRequestServiceImpl implements OtherRequestService {

    @Autowired
    OtherRequestDao otherRequestDao;

    @Override
    public List<Map<String, Object>> getGradeList() {
        return otherRequestDao.getGradeList();
    }

    @Override
    public List<Map<String, Object>> getSubjectList() {
        return otherRequestDao.getSubjectList();
    }
}
