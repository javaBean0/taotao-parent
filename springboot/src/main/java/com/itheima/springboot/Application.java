package com.itheima.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springboot的启动类（引导类）
 * @author bigStone
 *
 */
@SpringBootApplication//表明当前类是springboot的引导类
public class Application {

	public static void main(String[] args) {
		System.out.println("启动springboot");
		SpringApplication.run(Application.class, args);
		/*SpringApplication application = new SpringApplication(SpringApplication.class);
		application.setBannerMode(Mode.OFF);
		application.run(args);
*/
	}

}
