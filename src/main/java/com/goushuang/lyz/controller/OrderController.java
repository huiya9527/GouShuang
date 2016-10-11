package com.goushuang.lyz.controller;

import com.goushuang.lyz.dao.Customer;
import com.goushuang.lyz.dao.SystemOrder;
import com.goushuang.lyz.mapper.CustomerMapper;
import com.goushuang.lyz.mapper.SystemOrderMappper;
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
    private SystemOrderMappper systemOrderMappper;

    @Autowired
    private CustomerMapper customerMapper;

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String jump(Model model, String username, String[] bookname, int[] count, float[] price){
        // TODO: 2016/9/27 解耦，通过context获取
        SystemOrder systemOrder = new SystemOrder();
        // 追加orderInfo信息，表示订单详情
        for(int i = 0; i < bookname.length; i++){
            if(count[i] != 0){
                systemOrder.addItem(bookname[i], count[i], price[i]);
            }
        }
        systemOrder.setName(username);
        systemOrder.setTime(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG,   Locale.CHINESE).format(new java.util.Date()));
        systemOrder.setState("not pay");
        systemOrderMappper.insertOrder(systemOrder);
        Customer customer = customerMapper.findByName(username);
        model.addAttribute("systemOrder", systemOrder);
        model.addAttribute("customer",customer);
        return "payorder";
    }
}
