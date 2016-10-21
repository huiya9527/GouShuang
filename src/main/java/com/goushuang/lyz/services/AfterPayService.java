package com.goushuang.lyz.services;

import com.goushuang.lyz.dao.Book;
import com.goushuang.lyz.dao.Customer;
import com.goushuang.lyz.dao.SystemOrder;
import com.goushuang.lyz.mapper.BookMapper;
import com.goushuang.lyz.mapper.CustomerMapper;
import com.goushuang.lyz.mapper.SystemOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

@Component
public class AfterPayService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private SystemOrderMapper systemOrderMapper;

    public String afterPay(Model model, String username){
        List<Book> books = bookMapper.findAllBooks();
        Customer customer = customerMapper.findByName(username);
        List<SystemOrder> systemOrderList = systemOrderMapper.selectOrderByCustomer(username);
        model.addAttribute("user", customer);
        model.addAttribute("books", books);
        model.addAttribute("systemOrderList", systemOrderList);
        return "customer";
    }
}
