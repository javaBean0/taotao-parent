package com.taotao.controller;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;

public class TestPageHelper {

	@Test
	public void TestPageHelper(){
		//创建一个spring容器
				ApplicationContext application = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
				//从spring中获取mapper的代理对象
				TbItemMapper mapper = application.getBean(TbItemMapper.class);
				//执行查询，并分页
				TbItemExample example = new TbItemExample();
				PageHelper.startPage(1, 10);
				List<TbItem> list = mapper.selectByExample(example);
				for(TbItem tbItem : list){
					System.out.println(tbItem.getTitle());
				}
				
				//获取分页信息
				PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
				long total = pageInfo.getTotal();
				int num = pageInfo.getPageNum();
				int pageSize = pageInfo.getPageSize();
				System.out.println(num);
				System.out.println(pageSize);
				System.out.println(pageInfo.getPages());
				System.out.println(pageInfo.getSize());
				System.out.println("共有商品： " + total);
	}
}
