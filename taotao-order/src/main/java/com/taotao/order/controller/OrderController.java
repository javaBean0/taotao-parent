package com.taotao.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.order.pojo.Order;
import com.taotao.order.service.OrderService;

/**
 * 订单系统Controller
 * @author bigStone
 *
 */
@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;


	@RequestMapping(value="/create", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createOrder(@RequestBody Order order){
		try {
			return orderService.createOrder(order, order.getOrderItems(), order.getOrderShipping());
		} catch (Exception e) {
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
