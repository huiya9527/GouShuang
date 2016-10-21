package com.goushuang.lyz.mapper;

import com.goushuang.lyz.dao.SystemOrder;
import com.goushuang.lyz.viewObject.UserType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface SystemOrderMapper {

    @Insert("insert into systemorder (customer, totalPrice, state, time, info) values (#{customer}, #{totalPrice}, #{state}, #{time}, #{info}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertOrder(SystemOrder systemOrder);

    @Update("update systemorder set state = #{state} where id = #{id}")
    void updateStateById(@Param("state")String state, @Param("id")int id);

    @Update(("update systemorder set state = #{state}, courier = #{courier} where id = #{id}"))
    void updateStateAndCourierById(@Param("state")String state, @Param("courier")String courier, @Param("id")int id);

    @Select("select id, customer, courier, totalPrice, state, time, info from systemorder where customer = #{customer} and state = #{state")
    List<SystemOrder> selectOrderByCustomerAndState(@Param("customer")String customer, @Param("state")String state);

    @Select("select id, customer, courier, totalPrice, state, time, info from systemorder where courier = #{courier} and state= #{state}" )
    List<SystemOrder> selectOrderByCourierAndState(@Param("courier")String courier, @Param("state")String state);

    @Select("select id, customer, courier, totalPrice, state, time, info from systemorder where courier is null and state = 'paid'")
    List<SystemOrder> selectOrderCourierIsNullAndStateIsPaid();


}
