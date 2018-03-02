package com.shark.example.service;

import com.shark.base.entity.ResponseDataEntity;
import com.shark.base.service.BaseStringFromResponseDataService;
import com.shark.example.entity.MemberEntity;
import com.shark.example.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MemberService extends BaseStringFromResponseDataService<MemberEntity, MemberEntity> {

    public static final String INPUT_STRING_ACCOUNT = "account";

    @Autowired
    private MemberMapper memberMapper;

    @Override
    protected List<String> generateCheckKeyList() {
        List<String> list = new ArrayList<>();
        list.add(INPUT_STRING_ACCOUNT);
        return list;
    }

    @Override
    protected MemberEntity dataAccess(HashMap<String, String> parameters) throws Exception {
        System.out.println("MemberService memberMapper is null: " + (memberMapper == null));
        return memberMapper.selectMember(parameters.get(INPUT_STRING_ACCOUNT));
    }

    @Override
    protected ResponseDataEntity<MemberEntity> generateResultData(MemberEntity memberEntity) throws Exception {
        ResponseDataEntity<MemberEntity> responseDataEntity = new ResponseDataEntity();
        responseDataEntity.setReturnCode(1);
        responseDataEntity.setData(memberEntity);
        return responseDataEntity;
    }
}
