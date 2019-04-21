package com.taotao.rest.service.pojo;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;

/**
 * 内容处理Service层
 * @author bigStone
 *
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	
	
	@Override
	public List<TbContent> getContentList(Long categoryId) {
		//从缓存中	取数据
		try{
			String hget = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, categoryId + "");
			if(!StringUtils.isBlank(hget)){
				List<TbContent> resultList = JsonUtils.jsonToList(hget, TbContent.class);
				return resultList;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		//向缓存中添加内容
		try{
			//把list转换成字符串
			String cacheString = JsonUtils.objectToJson(list);
			jedisClient.hset(INDEX_CONTENT_REDIS_KEY, categoryId + "", cacheString);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
		
	}
	//编辑内容
	@Override
	public TaotaoResult editContent(TbContent content) {
		contentMapper.updateByPrimaryKey(content);
		HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
		return new TaotaoResult().ok();
	}

}
