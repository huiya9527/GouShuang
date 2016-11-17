package com.goushuang.lyz.controller;

import com.goushuang.lyz.dao.Administrator;
import com.goushuang.lyz.dao.Courier;
import com.goushuang.lyz.dao.Customer;
import com.goushuang.lyz.mapper.AdministratorMapper;
import com.goushuang.lyz.mapper.CourierMapper;
import com.goushuang.lyz.mapper.CustomerMapper;
import com.goushuang.lyz.tools.EncodeBySHA;
import com.goushuang.lyz.viewObject.UserType;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegistorController {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private AdministratorMapper administratorMapper;

    @Autowired
    private CourierMapper courierMapper;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@Param("username")String username, @Param("password")String password, @Param("usertype")String usertype){
        System.out.println(username+ " " + password + " " + usertype);
        if(usertype.equals(UserType.customer.getDescription())){
            Customer customer = customerMapper.findByName(username);
            if(customer != null) {
                return "registerError";
            }
            customerMapper.addCustomer(username, EncodeBySHA.encodebySHA(password), "");
        } else if(usertype.equals(UserType.courier.getDescription())){
            Courier courier = courierMapper.findByName(username);
            if(courier!= null) {
                return "registerError";
            }
            courierMapper.addCourier(username, EncodeBySHA.encodebySHA(password), "");
        } else {
            Administrator administrator = administratorMapper.findByName(username);
            if(administrator != null) {
                return "registerError";
            }
            administratorMapper.addAdministor(username, EncodeBySHA.encodebySHA(password), "");
        }
        return "registerSuccess";
    }
}
