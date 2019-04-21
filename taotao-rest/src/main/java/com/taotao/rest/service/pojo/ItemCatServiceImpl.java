package com.taotao.rest.service.pojo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${ITEMCAT_LIST_REDIS_KEY}")
	private String ITEMCAT_LIST_REDIS_KEY;

	@Override
	public CatResult getItemCatList() {
		//从redis中取数据
		try {
			String str = jedisClient.get(ITEMCAT_LIST_REDIS_KEY);
			if(!StringUtils.isBlank(str)){
				CatResult jsonToPojo = JsonUtils.jsonToPojo(str, CatResult.class);
				return jsonToPojo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		CatResult catResult = new CatResult();
		catResult.setData(getCatList(0));
		//向redis中存数据
		try{
			if((catResult.getData()!= null)){
				jedisClient.set(ITEMCAT_LIST_REDIS_KEY, JsonUtils.objectToJson(catResult));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return catResult;
	}

	private List<?> getCatList(long parentId) {
		// 创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		// 执行查询
		criteria.andParentIdEqualTo(parentId);
		// 返回List
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		// 返回值list
		List resultList = new ArrayList();
		int count = 0;
		for (TbItemCat tbItemCat : list) {
			if (tbItemCat.getIsParent()) {
				CatNode catNode = new CatNode();
				if (parentId == 0) {
					catNode.setName(
							"<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
				} else {
					catNode.setName(tbItemCat.getName());
				}
				catNode.setUrl("/products/" + tbItemCat.getId() + ".html");
				catNode.setItem(getCatList(tbItemCat.getId()));
				resultList.add(catNode);
				count++;
				if (count >= 14) {
					break;
				}
				// 如果是一个叶子节点
			} else {
				resultList.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName() + "");
			}
		}
		return resultList;
	}

}
