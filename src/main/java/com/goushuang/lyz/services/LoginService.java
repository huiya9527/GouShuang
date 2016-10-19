package com.goushuang.lyz.services;

import com.goushuang.lyz.dao.*;
import com.goushuang.lyz.error.LoginError;
import com.goushuang.lyz.mapper.*;
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
    private CourierMapper courierMapper;

    @Autowired
    private AdministratorMapper administratorMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private SystemOrderMappper systemOrderMappper;

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
            List<SystemOrder> systemOrderList = systemOrderMappper.selectOrderByCustomer(loginMessage.getUsername());
            model.addAttribute("user", customer);
            model.addAttribute("books", books);
            model.addAttribute("systemOrderList", systemOrderList);
            return "customer";
        } else if(loginMessage.getUserType().equals("courier")) {
            Courier courier = courierMapper.findByName(loginMessage.getUsername());
            if(courier == null){
                model.addAttribute("errorMessage", new LoginError("username does not exist!"));
                System.out.println("username does not exist!");
                return "loginerror";
            }
            if(!EncodeBySHA.encodebySHA(loginMessage.getPassword()).equals(courier.getPassword())){
                model.addAttribute("errorMessage", new LoginError("wrong password!"));
                System.out.println("wrong password!");
                return "loginerror";
            }
            return "courier";
        } else if(loginMessage.getUserType().equals("administrator")) {
            Administrator administrator = administratorMapper.findByName(loginMessage.getUsername());
            if(administrator == null){
                model.addAttribute("errorMessage", new LoginError("username does not exist!"));
                System.out.println("username does not exist!");
                return "loginerror";
            }
            if(!EncodeBySHA.encodebySHA(loginMessage.getPassword()).equals(administrator.getPassword())){
                model.addAttribute("errorMessage", new LoginError("wrong password!"));
                System.out.println("wrong password!");
                return "loginerror";
            }
            return "administrator";
        }
        model.addAttribute("errorMessage", new LoginError("error unknow!"));
        return "loginerror";
    }
}
