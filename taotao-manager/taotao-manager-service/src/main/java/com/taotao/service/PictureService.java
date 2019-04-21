package com.taotao.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传服务
 * @author bigStone
 *
 */
public interface PictureService {

	Map uploadPicture(MultipartFile uploadFile);
}
