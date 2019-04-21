package com.taotao.search.mapper;


import java.util.List;
import com.taotao.search.pojo.Item;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
public interface ItemMapper extends Mapper<Item> {

	@Select("SELECT a.id,a.title,a.sell_point,a.price,a.image,b.`name` category_name,c.item_desc FROM tb_item a LEFT JOIN tb_item_cat b ON a.cid = b.id LEFT JOIN tb_item_desc c ON a.id = c.item_id")
	 List<Item> getItemList();
	
	
	 
}
