package com.goushuang.lyz.services;

import com.goushuang.lyz.dao.Book;
import com.goushuang.lyz.dao.Customer;
import com.goushuang.lyz.dao.SystemOrder;
import com.goushuang.lyz.mapper.BookMapper;
import com.goushuang.lyz.mapper.CustomerMapper;
import com.goushuang.lyz.mapper.SystemOrderMapper;
import com.goushuang.lyz.viewObject.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;


@Service
@Transactional
public class TransactionService {

    @Autowired
    private SystemOrderMapper systemOrderMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private BookMapper bookMapper;

    public boolean payForOrder(SystemOrder systemOrder, Model model) throws RuntimeException{
        Customer customer = customerMapper.findByName(systemOrder.getCustomer());
        String info = systemOrder.getInfo();
        //判断用于余额是否充足
        if(systemOrder.getTotalPrice() > customer.getReserve()) {
            model.addAttribute("errorMessage", "not enough money!");
            return false;
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
                return false;
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
        return true;
    }

    public boolean acceptOrder(int id, String courier) throws RuntimeException{
        SystemOrder systemOrder = systemOrderMapper.selectOrderById(id);
        if(!systemOrder.getState().equals(OrderState.paid.getDescription())) {
            return false;
        }
        systemOrderMapper.updateStateAndCourierById(OrderState.deliver.getDescription(), courier, id);
        return true;
    }

    public boolean finishOrder(int id) throws RuntimeException{
        SystemOrder systemOrder = systemOrderMapper.selectOrderById(id);
        if(!systemOrder.getState().equals(OrderState.deliver.getDescription())) {
            return false;
        }
        systemOrderMapper.updateStateById(OrderState.finished.getDescription(),id);
        return true;
    }


}
