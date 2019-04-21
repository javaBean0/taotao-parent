package com.taotao.search.mapper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.search.pojo.Item;

public class TestMapper {

	@Test
	public void testMapper(){
		ApplicationContext application = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		ItemMapper bean = (ItemMapper) application.getBean("itemMapper");
		List<Item> itemList = bean.getItemList();
		for (Item item : itemList) {
			System.out.println(item);
			
		}
	}
	
}
