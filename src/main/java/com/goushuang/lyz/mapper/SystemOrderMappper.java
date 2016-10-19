package com.goushuang.lyz.mapper;

import com.goushuang.lyz.dao.SystemOrder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface SystemOrderMappper {

    @Insert("insert into systemorder (customer, totalPrice, state, time, info) values (#{customer}, #{totalPrice}, #{state}, #{time}, #{info}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertOrder(SystemOrder systemOrder);

    @Update("update systemorder set state = #{state} where id = #{id}")
    void updateStateById(@Param("state")String state, @Param("id")int id);

    @Select("select id, customer, courier, totalPrice, state, time, info from systemorder where customer = #{customer}")
    List<SystemOrder> selectOrderByCustomer(@Param("customer")String customer);

    @Select("select id, customer, courier, totalPrice, state, time, info from systemorder where courier = #{courier}")
    List<SystemOrder> selectOrderByCourier(@Param("courier")String courier);

    @Select("select id, customer, courier, totalPrice, state, time, info from systemorder where courier is null")
    List<SystemOrder> selectOrderCourierIsNull(@Param("courier")String courier);
}
