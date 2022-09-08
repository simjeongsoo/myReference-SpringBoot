package com.springboot.myreference.basicapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 프로젝트의 main class
// @SpringBootApplication 를 통해 스프링부트 자동생성 , Bean 읽기 생성 자동설정
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // SpringApplication.run 으로 내장 WAS 실행
        SpringApplication.run(Application.class, args);
    }
}
