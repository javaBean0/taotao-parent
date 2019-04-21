package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {

	//根据商品id查询商品信息
	TbItem getItemById(long Id);
	//获取商品列表
	EUDataGridResult getItemList(int page, int rows);
	//插入商品条目
	TaotaoResult createItem(TbItem item, String desc, String itemParam) throws Exception;
	
}
