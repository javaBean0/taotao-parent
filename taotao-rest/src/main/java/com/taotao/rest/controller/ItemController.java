package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;

/**
 * 商品基本信息管理service层
 * @author bigStone		
 *
 */
@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/info/{itemId}")
	public TaotaoResult getItemBaseInfo(@PathVariable Long itemId){
		return itemService.getItemBaseInfo(itemId);
	}
	
	@RequestMapping("/desc/{itemId}")
	public TaotaoResult getItemDesc(@PathVariable Long itemId){
		return itemService.getItemDesc(itemId);
	}
	
	@RequestMapping("/param/{itemId}")
	public TaotaoResult getItemParam(@PathVariable Long itemId){
		return itemService.getItemParam(itemId);
	}
	
}
