package com.goushuang.lyz.mapper;

import com.goushuang.lyz.dao.SystemOrder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface SystemOrderMappper {

    @Insert("insert into systemorder (name, totalPrice, state, time, info) values (#{name}, #{totalPrice}, #{state}, #{time}, #{info}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertOrder(SystemOrder systemOrder);

    @Update("update systemorder set state = #{state} where id = #{id}")
    void updateStateById(@Param("state")String state, @Param("id")int id);

    @Select("select id, courier, totalPrice, state, time, info from systemorder where name = #{name}")
    List<SystemOrder> selectOrderByName(@Param("name")String name);
}
