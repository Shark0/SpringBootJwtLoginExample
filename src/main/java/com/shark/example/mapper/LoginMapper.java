package com.shark.example.mapper;

import com.shark.example.entity.LoginEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {

    @Select("SELECT * FROM member WHERE account = #{account}")
    @Results({
            @Result(property = "id",  column = "id"),
            @Result(property = "account",  column = "account"),
            @Result(property = "password", column = "password"),
            @Result(property = "role1", column = "role1"),
            @Result(property = "role2", column = "role2")
    })
    public LoginEntity selectMember(String account);
}
