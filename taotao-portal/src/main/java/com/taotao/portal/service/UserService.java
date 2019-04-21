package com.taotao.portal.service;

import javax.servlet.http.HttpServletRequest;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;


public interface UserService {

	TbUser getUserByToken(String token);
	
	TaotaoResult userLogout(HttpServletRequest request);
}
