package com.goushuang.lyz.mapper;

import com.goushuang.lyz.dao.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface CustomerMapper {
    @Select("select name, password, reserve from customers where name = #{name}")
    Customer findByName(@Param("name")String name);
}
