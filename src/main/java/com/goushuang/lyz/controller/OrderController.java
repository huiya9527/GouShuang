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

import java.text.DateFormat;
import java.util.Locale;

@Controller
public class OrderController {

    @Autowired
    private SystemOrderMappper systemOrderMappper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private BookMapper bookMapper;

    /***
     * 用户在前端页面填写好订单信息并提交后，此处会将代付款订单写入数据库。
     * 正常返回订单确认付款页面
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
        systemOrder.setName(username);
        systemOrder.setTime(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG,   Locale.CHINESE).format(new java.util.Date()));
        systemOrder.setState("not pay");
        systemOrderMappper.insertOrder(systemOrder);
        Customer customer = customerMapper.findByName(username);
        model.addAttribute("systemOrder", systemOrder);
        model.addAttribute("customer",customer);
        return "payorder";
    }

    /***
     * 用户确认付款后，完成更改数据库状态（用户余额，书籍余量，订单状态）
     * 正常返回付款成功页面。
     */
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


    /***
     * 对于已经提交过的订单，用户可以修改状态。具体策略如下：
     * 1、对于未付款订单，可以选择付款或者取消订单。
     * 2、对于已经失效的订单，暂时不做任何处理。
     * 3、对于已经付款的订单，可以选择退款或者申请退款。
     * 4、对于已经配送完成的订单，可以确认收货或者申请退款。
     * 5、对于已经确认收货的订单，暂时不做任何处理。
     */
    @RequestMapping(value = "existorder", method = RequestMethod.POST)
    public String existOrder(Model model, String username){
        return "";
    }
}
