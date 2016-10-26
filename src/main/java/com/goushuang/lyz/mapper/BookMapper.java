package com.goushuang.lyz.mapper;

import com.goushuang.lyz.dao.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BookMapper {
    @Select("select id, name, num, price, classify, info from book where classify = #{classify}")
    List<Book> findByClassify(@Param("classify")String classify);

    @Select("select id, name, num, price, classify, info from book")
    List<Book> findAllBooks();

    @Select("select id, name, num, price, classify, info from book where name = #{name}")
    Book findBookByName(@Param("name")String name);

    @Update("update book set num = #{num} where name = #{name}")
    void updateBookNumByName(@Param("num")int num, @Param("name")String name);
}
