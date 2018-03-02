package com.shark.example.mapper;

import com.shark.example.entity.MemberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {

    @Select("SELECT * FROM member WHERE account = #{account}")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "name", column = "name")
    })
    public MemberEntity selectMember(String account);
}
