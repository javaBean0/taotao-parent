package com.taotao.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.taotao.common.utils.FtpUtil;

public class FTPTest {

	@Test
	public void testFtpClient() throws Exception{
		//创建一个FtpClient对象
		FTPClient ftpClient = new FTPClient();
		//创建ftp连接，默认端口号是21
		ftpClient.connect("192.168.0.126", 21);
		//登录ftp服务器
		ftpClient.login("ftpuser", "root");
		//上传文件
		FileInputStream inputStream = new FileInputStream(new File("F:\\398套简历（手机端 已解压 简历分类清晰）\\A. 130套四页模板（封面+简历+自荐信+封底）\\1.商务类\\预览图（方便查看，不能修改）\\套餐8(1).JPG"));
		//设置上传路径
		ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
		//修改上传文件的格式
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		//第一个参数:服务器的文档名
		//第二个参数：上传文档的inputStream
		ftpClient.storeFile("hello.jpg", inputStream);
		ftpClient.logout();
	}
	
	
	@Test
	public void TestFTPUtils() throws FileNotFoundException{
		FileInputStream inputStream = new FileInputStream(new File("F:\\398套简历（手机端 已解压 简历分类清晰）\\A. 130套四页模板（封面+简历+自荐信+封底）\\1.商务类\\预览图（方便查看，不能修改）\\套餐8(1).JPG"));
		FtpUtil.uploadFile("192.168.0.126", 21, "ftpuser", "root", "/home/ftpuser/www/images", "/2019/03/08", "hello.jpg", inputStream);
		
	}
}
