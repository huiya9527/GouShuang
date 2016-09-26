package com.goushuang.lyz.services;

import com.goushuang.lyz.dao.Book;
import com.goushuang.lyz.dao.Customer;
import com.goushuang.lyz.error.LoginError;
import com.goushuang.lyz.mapper.BookMapper;
import com.goushuang.lyz.mapper.CustomerMapper;
import com.goushuang.lyz.tools.EncodeBySHA;
import com.goushuang.lyz.viewObject.LoginMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

@Component
public class LoginService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private BookMapper bookMapper;

    public String loginCheck(LoginMessage loginMessage, Model model){
        if(loginMessage.getUserType().equals("customer")) {
            Customer customer = customerMapper.findByName(loginMessage.getUsername());
            if(customer == null) {
                model.addAttribute("errorMessage", new LoginError("username does not exist!"));
                System.out.println("username does not exist!");
                return "loginerror";
            }
            if(!EncodeBySHA.encodebySHA(loginMessage.getPassword()).equals(customer.getPassword())){
                model.addAttribute("errorMessage", new LoginError("wrong password!"));
                System.out.println("wrong password!");
                return "loginerror";
            }
            List<Book> books = bookMapper.findAllBooks();
            model.addAttribute("user", customer);
            model.addAttribute("books", books);
            return "customer";
        } else if(loginMessage.getUserType().equals("courier")) {
            return "courier";
        } else if(loginMessage.getUserType().equals("administrator")) {
            return "administrator";
        }
        return "loginerror";
    }
}
