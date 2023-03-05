package com.esliceu.Forum;

import com.esliceu.Forum.Interceptors.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ForumApplication implements WebMvcConfigurer {

	@Autowired
	TokenInterceptor tokenInterceptor;

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(tokenInterceptor)
				.addPathPatterns("/getprofile")
				.addPathPatterns("/topics")
				.addPathPatterns("/topics/{topicId}/replies");

	}


}
