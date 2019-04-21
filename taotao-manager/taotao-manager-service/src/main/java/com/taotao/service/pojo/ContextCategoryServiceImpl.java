package com.taotao.service.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;
@Service
public class ContextCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	public List<EUTreeNode> getCategoryList(long parentId) {
		//根据parentId查询节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EUTreeNode> resultList = new ArrayList<EUTreeNode>();
		for (TbContentCategory contentCategory : list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(contentCategory.getId());
			node.setState(contentCategory.getIsParent()?"closed":"open");
			node.setText(contentCategory.getName());
			resultList.add(node);
		}
		return resultList;
	}

	/**
	 *插入一个节点
	 */
	public TaotaoResult insertContentCategory(long parentId, String name) {
		//创建一个pojo
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		contentCategory.setUpdated(new Date());
		contentCategory.setCreated(new Date());
		//添加记录
		contentCategoryMapper.insert(contentCategory);
		//查看该节点的父节点的isParent是否为true， 如果部位true，改为true
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parentCat.getIsParent()){
			parentCat.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		//返回结果
		return TaotaoResult.ok(contentCategory);
	}
	
	public TaotaoResult deleteContentCategory(Long id){
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
		Long parentId = category.getParentId();
		 contentCategoryMapper.deleteByPrimaryKey(id);
		 TbContentCategoryExample example = new TbContentCategoryExample();
		 Criteria criteria = example.createCriteria();
		 criteria.andParentIdEqualTo(parentId);
		 List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		 if(list.isEmpty()){
			 TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
			 parentCat.setIsParent(false);
			 contentCategoryMapper.updateByPrimaryKey(parentCat);
		 }
		return TaotaoResult.ok();
	}

	@Override
	public void updateContentCategory(Long id, String name) {
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		contentCategory.setName(name);
		contentCategoryMapper.updateByPrimaryKey(contentCategory);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
