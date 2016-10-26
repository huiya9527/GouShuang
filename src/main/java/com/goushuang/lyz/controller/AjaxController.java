package com.goushuang.lyz.controller;

import com.goushuang.lyz.dao.SystemOrder;
import com.goushuang.lyz.mapper.SystemOrderMapper;
import com.goushuang.lyz.services.TransactionService;
import com.goushuang.lyz.viewObject.OrderState;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/ajax")
public class AjaxController {

    @Autowired
    SystemOrderMapper systemOrderMapper;

    @Autowired
    TransactionService transactionService;

    @Autowired
    MemcachedClient memcachedClient;

    /***
     * 快递员接受新的订单。
     * @param courier 快递员姓名
     * @param id  订单id
     * @return  订单更改状态
     */
    @RequestMapping(value="/acceptorder" ,method = RequestMethod.POST)
    public String acceptOrder(@RequestParam("courier")String courier, @RequestParam("id")int id){
        try{
            if(transactionService.acceptOrder(id,courier)){
                return "success";
            }
        }
       catch (Exception e){
           e.printStackTrace();
       }
       return "failure";
    }

    /***
     * 快递员或者用户确认订单送达。
     * @param id  订单id
     * @return  订单更改状态
     */
    @RequestMapping(value="/finishorder" ,method = RequestMethod.POST)
    public String finishOrder(@RequestParam("id")int id){
        try{
           if(transactionService.finishOrder(id)){
               return "success";
           }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "failure";
    }

    @RequestMapping(value="smsCode", method = RequestMethod.POST)
    public String getSmsCode(@RequestParam("customer")String customer, @RequestParam("id")int id){
        Random random = new Random();
        int code = random.nextInt(8999) + 1000;
        //重要，这里模拟发送短信验证码！
        String msg = customer + "_" + id;
        System.out.println(msg + "短信验证码为："+code);
        Future<Boolean> fo = memcachedClient.set(msg,30,code);
        try{
            if(fo.get()){
                return "success";
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "false";
    }
}
