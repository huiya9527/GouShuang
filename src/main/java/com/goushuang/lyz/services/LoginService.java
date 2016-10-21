package com.goushuang.lyz.services;

import com.goushuang.lyz.dao.*;
import com.goushuang.lyz.error.LoginError;
import com.goushuang.lyz.mapper.*;
import com.goushuang.lyz.tools.EncodeBySHA;
import com.goushuang.lyz.viewObject.LoginMessage;
import com.goushuang.lyz.viewObject.OrderState;
import com.goushuang.lyz.viewObject.UserType;
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
    private SystemOrderMapper systemOrderMapper;

    public String loginCheck(LoginMessage loginMessage, Model model){
        if(loginMessage.getUserType().equals(UserType.customer.getDescription())) {
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
        } else if(loginMessage.getUserType().equals(UserType.courier.getDescription())) {
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
            List<SystemOrder> myDeliverOrder = systemOrderMapper.selectOrderByCourierAndState(loginMessage.getUsername(), OrderState.deliver.getDescription());
            List<SystemOrder> myFinishedOrder = systemOrderMapper.selectOrderByCourierAndState(loginMessage.getUsername(), OrderState.finished.getDescription());
            List<SystemOrder> freeToCarryOrder = systemOrderMapper.selectOrderCourierIsNullAndStateIsPaid();
            model.addAttribute("myDeliverOrder", myDeliverOrder);
            model.addAttribute("myFinisheOrder", myFinishedOrder);
            model.addAttribute("freeToCarryOrder", freeToCarryOrder);
            model.addAttribute("courier", loginMessage.getUsername());
            return "courier";
        } else if(loginMessage.getUserType().equals(UserType.administrator.getDescription())) {
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
