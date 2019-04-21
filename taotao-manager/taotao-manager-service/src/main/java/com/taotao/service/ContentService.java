package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
/**
 * 内容管理service接口
 * @author bigStone
 *
 */
public interface ContentService {

	EUDataGridResult getContentList(int page, int rows, Long id);

	TaotaoResult save(TbContent content);

}
