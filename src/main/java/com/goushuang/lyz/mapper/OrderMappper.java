package com.goushuang.lyz.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface OrderMappper {

    @Insert("insert into customerorder (name, totalPrice, state, time, info) values (#{name}, #{totalPrice}, #{state}, #{time}, #{info}) " )
    void insertOrder(@Param("name")String name, @Param("totalPrice")float totalPrice, @Param("state")String state, @Param("time")String time, @Param("info")String info);
}
