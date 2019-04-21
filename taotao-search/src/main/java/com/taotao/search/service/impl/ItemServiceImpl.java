package com.taotao.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.pojo.Item;
import com.taotao.search.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;

	public TaotaoResult importAllItems() {
		try {
			//查询商品列表
			List<Item> list = itemMapper.getItemList();
			//把商品信息写入索引库
			for (Item item : list) {
				//创建一个solrInputDocument对象
				SolrInputDocument doc = new SolrInputDocument();
				doc.setField("id", item.getId());
				doc.setField("item_title", item.getTitle());
				doc.setField("item_sell_point", item.getSell_point());
				doc.setField("item_price", item.getPrice());
				doc.setField("item_image", item.getImage());
				doc.setField("item_category_name", item.getCategory_name());
				doc.setField("item_desc", item.getItem_des());
				solrServer.add(doc);
				
			}
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
			TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		} 
		return TaotaoResult.ok();
	}
	
	
}
