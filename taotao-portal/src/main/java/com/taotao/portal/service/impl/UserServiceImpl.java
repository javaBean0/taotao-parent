package com.taotao.portal.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;

/**
 * 用户管理service
 * @author bigStone
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Value("${SSO_BASE_URL}")
	public String SSO_BASE_URL;
	@Value("${SSO_USER_TOKEN}")
	private String SSO_USER_TOKEN;
	@Value("${SSO_PAGE_URL}")
	public String SSO_PAGE_URL;
	@Value("${SSO_USER_LOGOUT}")
	private String SSO_USER_LOGOUT;
	
	@Override
	public TbUser getUserByToken(String token) {
		try {
			//调用sso系统的服务，根据token取用户信息
			String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN + token);
			//把json转换成TaotaoResult
			TaotaoResult result = TaotaoResult.formatToPojo(json, TbUser.class);
			if(result.getStatus() == 200){
				TbUser user = (TbUser)result.getData();
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public TaotaoResult userLogout(HttpServletRequest request) {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		if(token != null) {
			String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_LOGOUT + token);
			TaotaoResult result = TaotaoResult.format(json);
			if(result.getStatus() == 200) {
				return TaotaoResult.ok();
			}
			return result;
		}
		return TaotaoResult.build(405, "登录信息过期，无需退出！");
	}

	
	
}
