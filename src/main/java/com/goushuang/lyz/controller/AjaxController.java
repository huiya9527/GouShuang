package com.goushuang.lyz.controller;

import com.goushuang.lyz.mapper.SystemOrderMapper;
import com.goushuang.lyz.viewObject.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ajax")
public class AjaxController {

    @Autowired
    SystemOrderMapper systemOrderMapper;

    /***
     * 快递员接受新的订单。
     * @param courier 快递员姓名
     * @param id  订单id
     * @return  订单更改状态
     */
    @RequestMapping(value="/acceptorder" ,method = RequestMethod.POST)
    public String acceptOrder(@RequestParam("courier")String courier, @RequestParam("id")int id){
        try{
            //// TODO: 2016/10/20 这个地方没有解决同步的问题，肯定要解决，应该先查询订单状态再更新
            systemOrderMapper.updateStateAndCourierById(OrderState.deliver.getDescription(), courier,id);
            return "success";
        }
       catch (Exception e){
           e.printStackTrace();
           return "failure";
       }
    }

    /***
     * 快递员确认订单送达。
     * @param id  订单id
     * @return  订单更改状态
     */
    @RequestMapping(value="/finishorder" ,method = RequestMethod.POST)
    public String finishOrder(@RequestParam("id")int id){
        try{
            //// TODO: 2016/10/20 这个地方没有解决同步的问题，肯定要解决，应该先查询订单状态再确认
            systemOrderMapper.updateStateById(OrderState.finished.getDescription(),id);
            return "success";
        }
        catch (Exception e){
            e.printStackTrace();
            return "failure";
        }
    }

}
