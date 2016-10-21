package com.goushuang.lyz.controller;

import com.goushuang.lyz.dao.Book;
import com.goushuang.lyz.dao.Customer;
import com.goushuang.lyz.dao.SystemOrder;
import com.goushuang.lyz.mapper.BookMapper;
import com.goushuang.lyz.mapper.CustomerMapper;
import com.goushuang.lyz.mapper.SystemOrderMapper;
import com.goushuang.lyz.viewObject.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

@Controller
public class OrderController {

    @Autowired
    private SystemOrderMapper systemOrderMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private BookMapper bookMapper;

    /***
     * 订单提交
     * @return 确认付款
     */
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String order(Model model, String username, String[] bookname, int[] count, float[] price){
        // TODO: 2016/9/27 解耦，通过context获取
        SystemOrder systemOrder = new SystemOrder();
        // 追加orderInfo信息，表示订单详情
        for(int i = 0; i < bookname.length; i++){
            if(count[i] != 0){
                systemOrder.addItem(bookname[i], count[i], price[i]);
            }
        }
        systemOrder.setCustomer(username);
        systemOrder.setTime(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG,   Locale.CHINESE).format(new java.util.Date()));
        systemOrder.setState(OrderState.notPay.getDescription());
        systemOrderMapper.insertOrder(systemOrder);
        Customer customer = customerMapper.findByName(username);
        model.addAttribute("systemOrder", systemOrder);
        model.addAttribute("customer",customer);
        return "payorder";
    }

    /***
     * 确认付款
     * @return 付款成功
     */
    @RequestMapping(value="/paying", method= RequestMethod.POST)
    public String payOrder(@ModelAttribute SystemOrder systemOrder, Model model){
        String info = systemOrder.getInfo();
        Customer customer = customerMapper.findByName(systemOrder.getCustomer());
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
        systemOrderMapper.updateStateById(OrderState.paid.getDescription(), systemOrder.getId());
        //扣款
        customerMapper.updateReverseByName(customer.getReserve()- systemOrder.getTotalPrice(), customer.getName());
        return "finishpay";
    }


    /***
     * 对于已经提交过的订单，用户可以修改状态。具体策略如下：
     * 1、对于未付款订单，可以选择付款或者取消订单。
     * 2、对于已经付款的订单，可以选择退款。
     */
    @RequestMapping(value = "customerexistorder", method = RequestMethod.POST)
    public String existOrder(Model model, String username) {
        List<SystemOrder> orderNotPay = systemOrderMapper.selectOrderByCustomerAndState(username, OrderState.notPay.getDescription());
        List<SystemOrder> orderPaid = systemOrderMapper.selectOrderByCustomerAndState(username, OrderState.paid.getDescription());
        List<SystemOrder> orderFinished = systemOrderMapper.selectOrderByCustomerAndState(username, OrderState.finished.getDescription());
        List<SystemOrder> orderCancelled = systemOrderMapper.selectOrderByCustomerAndState(username, OrderState.cancelled.getDescription());
        model.addAttribute("orderNotPay", orderNotPay);
        model.addAttribute("orderPaid", orderPaid);
        model.addAttribute("orderFinished",orderFinished);
        model.addAttribute("orderCancelled",orderCancelled);

        return "";
    }
}
