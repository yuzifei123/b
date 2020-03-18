package com.education.uv.Service.Impl;

import com.education.uv.Dao.SectionDao;
import com.education.uv.Service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    SectionDao sectionDao;

    @Override
    public List<Map<String, Object>> getSectionList(int pageSize, int page, Map<String, Object> searchMap) {
        return sectionDao.getSectionList(pageSize,page,searchMap);
    }

    @Override
    public String getSectionTotal(Map<String, Object> searchMap) {
        return sectionDao.getSectionTotal(searchMap);
    }

    @Override
    public List<Map<String, Object>> getSectionDetails(String sectionId) {
        return sectionDao.getSectionDetails(sectionId);
    }

    @Override
    public int insertSection(Map<String, Object> parameter) {
        return sectionDao.insertSection(parameter);
    }

    @Override
    public int updateSection(Map<String, Object> parameter) {
        return sectionDao.updateSection(parameter);
    }

    @Override
    public int deleteSection(String sectionId) {
        return sectionDao.deleteSection(sectionId);
    }
}
