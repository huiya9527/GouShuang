package com.goushuang.lyz.controller;

import com.goushuang.lyz.dao.Order;
import com.goushuang.lyz.mapper.OrderMappper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DateFormat;
import java.util.Locale;

@Controller
public class OrderController {

    @Autowired
    private OrderMappper orderMappper;

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String jump(Model model, String username, String[] bookname, int[] count, float[] price){
        // TODO: 2016/9/27 解耦，通过context获取
        Order order = new Order();
        for(int i = 0; i < bookname.length; i++){
            if(count[i] != 0){
                order.addItem(bookname[i], count[i], price[i]);
            }
        }
        order.setUserName(username);
        order.setTime(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG,   Locale.CHINESE).format(new java.util.Date()));
        order.setState("not pay");
        orderMappper.insertOrder(order.getUserName(), order.getTotalPrice(), order.getState(), order.getTime(), order.getInfo());
        model.addAttribute("order", order);
        return "payorder";
    }
}
