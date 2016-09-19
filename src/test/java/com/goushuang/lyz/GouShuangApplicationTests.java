package com.goushuang.lyz;

import com.goushuang.lyz.mapper.BookMapper;
import com.goushuang.lyz.dao.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GouShuangApplicationTests {

	@Autowired
	private BookMapper bookMapper;

	@Test
	public void contextLoads() {
	}

	@Test
	public void findByName() throws Exception {
		List<Book> bookList = bookMapper.findByClassify("小说");
        for(Book book: bookList){
            System.out.println(book.getId());
        }
    }

}
