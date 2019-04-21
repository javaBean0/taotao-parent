package com.taotao.order.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.dao.JedisClient;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	@Autowired
	private JedisClient JedisClient;
	@Value("${ORDER_DETAIL_KEY}")
	private String ORDER_DETAIL_KEY;
	@Value("${ORDER_GEN_KEY}")
	private String ORDER_GEN_KEY;
	@Value("${ORDER_INIT_ID}")
	private String ORDER_INIT_ID;
	
	@Override
	public TaotaoResult createOrder(TbOrder order, List<TbOrderItem> itemList, TbOrderShipping orderShipping) {
		
		//获得订单号
		//向订单表中插入数据
		String string = JedisClient.get(ORDER_GEN_KEY);
		if(StringUtils.isBlank(string)){
			JedisClient.set(ORDER_GEN_KEY, ORDER_INIT_ID);
		}
		long orderId = JedisClient.incr(ORDER_GEN_KEY);
		//补全pojo的属性
		order.setOrderId(orderId + "");
		//1.表示未付款， 2，表示已经付款 3， 未发货 4， 已发货， 5，交易成功 6， 交易关闭
		order.setStatus(1);
		Date date = new Date();
		order.setCreateTime(date);
		order.setUpdateTime(date);
		//0,表示未评价， 1.表示已经评价
		order.setBuyerRate(0);
		//向订单表中插入数据
		orderMapper.insert(order);
		
		
		//插入订单明细
		for (TbOrderItem tbOrderItem : itemList) {
			//补全订单明细
			//取订单明细id
			long orderDetailId = JedisClient.incr(ORDER_DETAIL_KEY);
			tbOrderItem.setId(orderDetailId + "");
			tbOrderItem.setOrderId(orderId + "");
			//向订单明细表中插入数据
			orderItemMapper.insert(tbOrderItem);
		}
		//插入物流表
		orderShipping.setOrderId(orderId + "");
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		return TaotaoResult.ok(orderId);
	}

}
