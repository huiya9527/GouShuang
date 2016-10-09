package com.goushuang.lyz.controller;

import com.goushuang.lyz.dao.Customer;
import com.goushuang.lyz.dao.Order;
import com.goushuang.lyz.mapper.CustomerMapper;
import org.apache.catalina.util.CustomObjectInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PayOrderController {
    @Autowired
    private CustomerMapper customerMapper;

    @RequestMapping(value="/paying", method= RequestMethod.POST)
    public String payOrder(@ModelAttribute Order order, Model model){
        String info = order.getInfo();
        Customer customer = customerMapper.findByName(order.getUserName());
        if(order.getTotalPrice() > customer.getReserve()) {
            model.addAttribute("errorMessage", "not enough money!");
            return "payError";
        }
        String[] strs = info.split(" ");
        System.out.println(order.getInfo());
        System.out.println(order.getUserName());
        return "finishpay";
    }
}
