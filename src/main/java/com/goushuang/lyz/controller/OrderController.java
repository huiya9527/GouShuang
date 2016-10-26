package com.goushuang.lyz.controller;

import com.goushuang.lyz.dao.Book;
import com.goushuang.lyz.dao.Customer;
import com.goushuang.lyz.dao.SystemOrder;
import com.goushuang.lyz.mapper.BookMapper;
import com.goushuang.lyz.mapper.CustomerMapper;
import com.goushuang.lyz.mapper.SystemOrderMapper;
import com.goushuang.lyz.services.TransactionService;
import com.goushuang.lyz.viewObject.OrderState;
import net.spy.memcached.MemcachedClient;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
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
    TransactionService transactionService;

    @Autowired
    MemcachedClient memcachedClient;

    /***
     * 订单提交
     * @return 确认付款
     */
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String order(Model model, String username, String[] bookname, int[] count, float[] price){
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
     * 查看订单详情
     * @return 确认付款
     */
    @RequestMapping(value = "/order2", method = RequestMethod.POST)
    public String order(Model model, @ModelAttribute("systemOrder")SystemOrder systemOrder){
        Customer customer = customerMapper.findByName(systemOrder.getCustomer());
        model.addAttribute("systemOrder", systemOrder);
        model.addAttribute("customer",customer);
        return "payorder";
    }

    /***
     * 确认付款（用于下单后立刻付款的情况）
     * @return 付款成功
     */
    @RequestMapping(value="/paying", method= RequestMethod.POST)
    public String payOrder(@ModelAttribute("systemOrder")SystemOrder systemOrder, @ModelAttribute("smscode")int smscode, Model model){
        String msg = systemOrder.getCustomer() + "_" + systemOrder.getId();
        if(memcachedClient.get(msg)==null || (int)memcachedClient.get(msg) != smscode){
            model.addAttribute("errorMessage","验证码错误或已过期");
            return "payerror";
        }
        try{
            if(transactionService.payForOrder(systemOrder,model)){
                return "finishpay";
            }
        } catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
        }
        return "payerror";
    }

    @RequestMapping(value = "/customerexistorder", method = RequestMethod.POST)
    public String existOrder(Model model, String username) {
        List<SystemOrder> orderNotPay = systemOrderMapper.selectOrderByCustomerAndState(username, OrderState.notPay.getDescription());
        List<SystemOrder> orderPaid = systemOrderMapper.selectOrderByCustomerAndState(username, OrderState.paid.getDescription());
        List<SystemOrder> orderDeliver = systemOrderMapper.selectOrderByCustomerAndState(username, OrderState.deliver.getDescription());
        List<SystemOrder> orderFinished = systemOrderMapper.selectOrderByCustomerAndState(username, OrderState.finished.getDescription());
        List<SystemOrder> orderCancelled = systemOrderMapper.selectOrderByCustomerAndState(username, OrderState.cancelled.getDescription());
        model.addAttribute("orderNotPay", orderNotPay);
        model.addAttribute("orderPaid", orderPaid);
        model.addAttribute("orderDeliver", orderDeliver);
        model.addAttribute("orderFinished",orderFinished);
        model.addAttribute("orderCancelled",orderCancelled);
        model.addAttribute("username",username);
        return "customerorder";
    }
}
