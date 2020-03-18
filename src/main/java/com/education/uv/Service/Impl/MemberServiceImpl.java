package com.education.uv.Service.Impl;

import com.education.uv.Dao.MemberDao;
import com.education.uv.Dao.StudentDao;
import com.education.uv.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public List<Map<String, Object>> getMemberList(int pageSize, int page, Map<String, Object> searchMap) {
        return memberDao.getMemberList(pageSize, page, searchMap);
    }

    @Override
    public String getMemberTotal(Map<String, Object> searchMap) {
        return memberDao.getMemberTotal(searchMap);
    }

    @Override
    public List<Map<String, Object>> getMemberDetails(String studentId) {
        return memberDao.getMemberDetails(studentId);
    }

    @Override
    public int insertMember(Map<String, Object> parameter) {
        return memberDao.insertMember(parameter);
    }

    @Override
    public int updateMember(Map<String, Object> parameter) {
        return memberDao.updateMember(parameter);
    }

    @Override
    public int deleteMember(String studentId) {
        return memberDao.deleteMember(studentId);
    }
}
