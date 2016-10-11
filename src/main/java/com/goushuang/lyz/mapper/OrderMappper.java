package com.goushuang.lyz.mapper;

import com.goushuang.lyz.dao.SystemOrder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface OrderMappper {

    @Insert("insert into order (name, totalPrice, state, time, info) values (#{name}, #{totalPrice}, #{state}, #{time}, #{info}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertOrder(SystemOrder systemOrder);

    @Update("update order set state = #{state} where id = #{id}")
    void updateStateById(@Param("state")String state, @Param("id")int id);

}
