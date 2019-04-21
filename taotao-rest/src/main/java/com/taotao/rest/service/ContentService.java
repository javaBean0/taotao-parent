package com.taotao.rest.service;

import java.util.List;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

/**
 * 内容管理Service接口
 * @author bigStone
 *
 */
public interface ContentService {

	List<TbContent> getContentList(Long categoryId);

	TaotaoResult editContent(TbContent content);
}
