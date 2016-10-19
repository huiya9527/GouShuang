package com.goushuang.lyz.mapper;

import com.goushuang.lyz.dao.Courier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CourierMapper {
    @Select("select name, password from courier where name = #{name}")
    Courier findByName(@Param("name")String name);
}
