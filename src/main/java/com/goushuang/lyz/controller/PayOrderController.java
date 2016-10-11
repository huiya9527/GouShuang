package com.goushuang.lyz.controller;

import com.goushuang.lyz.dao.Book;
import com.goushuang.lyz.dao.Customer;
import com.goushuang.lyz.dao.SystemOrder;
import com.goushuang.lyz.mapper.BookMapper;
import com.goushuang.lyz.mapper.CustomerMapper;
import com.goushuang.lyz.mapper.SystemOrderMappper;
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

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private SystemOrderMappper systemOrderMappper;

    @RequestMapping(value="/paying", method= RequestMethod.POST)
    public String payOrder(@ModelAttribute SystemOrder systemOrder, Model model){
        String info = systemOrder.getInfo();
        Customer customer = customerMapper.findByName(systemOrder.getName());
        //判断用于余额是否充足
        if(systemOrder.getTotalPrice() > customer.getReserve()) {
            model.addAttribute("errorMessage", "not enough money!");
            return "payError";
        }
        String[] strs = info.split(" ");
        //依次处理每一本书
        for (String s: strs){
            String[] ss = s.split(",");
            String name = ss[0];
            int count = Integer.parseInt(ss[1]);
            Book book = bookMapper.findBookByName(name);
            //判断书是否存在
            if (book == null){
                model.addAttribute("errorMessage", "the book " + name + " does not exist!");
                return "payError";
            }
            //判断书余量是否充足
            if (book.getNum() < count) {
                model.addAttribute("errorMessage", "the book " + name + " is insufficient!");
            }
            //更新书的余量
            bookMapper.updateBookNumByName(book.getNum()-count, name);
        }
        //查询订单并修改状态
        systemOrderMappper.updateStateById("paid", systemOrder.getId());
        //扣款
        customerMapper.updateReverseByName(customer.getReserve()- systemOrder.getTotalPrice(), customer.getName());
        return "finishpay";
    }
}
