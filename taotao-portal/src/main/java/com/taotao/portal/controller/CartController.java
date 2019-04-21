package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.UserService;

/**
 * 购物城的Controller
 * @author bigStone
 *
 */
@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	@Autowired
	private UserService userService;
	
	@RequestMapping("/add/{itemId}")
	public String addCartItem(@PathVariable() Long itemId, 
			@RequestParam(defaultValue="1") Integer num, 
			HttpServletRequest request, HttpServletResponse response){
		TaotaoResult result = cartService.addCartItem(itemId, num, request, response);
		return "redirect:/cart/success.html";
	}
	
	@RequestMapping("/success")
	public String showSuccess(){
		return "cartSuccess";
	}
	
	@RequestMapping("/cart")
	public String showCart(HttpServletRequest request, HttpServletResponse response, Model model){
		List<CartItem> itemList = cartService.getCartItemList(request, response);
		model.addAttribute("cartList", itemList);
		return "cart";
	}
	
	@RequestMapping("/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response){
		TaotaoResult result = cartService.deleteCartItem(itemId, request, response);
		return "redirect:/cart/success.html";
	}
	
	
	
	
	
	
	
	
	
}