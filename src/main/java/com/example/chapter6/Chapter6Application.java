package com.example.chapter6;

import com.example.chapter6.Util.AppConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Chapter6Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter6Application.class, args);
	}

	@Bean(name="uploadPath")
	public String uploadPath(){
		return AppConstants.ATTACH_DIR_NAME;
	}


}
