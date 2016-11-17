package com.goushuang.lyz.mapper;

import com.goushuang.lyz.dao.Customer;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface CustomerMapper {
    @Select("select name, password, reserve from customer where name = #{name}")
    Customer findByName(@Param("name")String name);

    @Update("update customer set reserve = #{reverse} where name = #{name}")
    void updateReverseByName(@Param("reverse")float reverse, @Param("name")String name);

    @Insert("insert into customer (name, password, info) values (#{name}, #{password}, #{info}) ")
    void addCustomer(@Param("name")String name, @Param("password")String password, @Param("info")String info);
}
