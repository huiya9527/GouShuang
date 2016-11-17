package com.goushuang.lyz.mapper;

import com.goushuang.lyz.dao.Administrator;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AdministratorMapper {

    @Select("select name, password, info from administrator where name = #{name}")
    Administrator findByName(@Param("name")String name);

    @Insert("insert into administrator (name, password, info) values (#{name}, #{password}, #{info}) ")
    void addAdministor(@Param("name")String name, @Param("password")String password, @Param("info")String info);

}
