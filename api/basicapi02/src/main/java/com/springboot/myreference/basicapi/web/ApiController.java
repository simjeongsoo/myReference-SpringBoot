package com.springboot.myreference.basicapi.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController { // client 단과 만나는 Controller

    @RequestMapping(value = "/api/test",method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public String getApiTest() {
        return "{\"result\":\"ok\"}";
    }

    @RequestMapping(value = "/api/test2",method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public String getApiTest2() {
        return  "{\"result\":\"ok\"}";
    }
    /*
    @RequestMapping(value = "/api/test",method = RequestMethod.GET)
        getApiTest() 메소드를 호출하기위한 설정
        value = "/api/test"  : 호출할때 이런 설정으로 호출
        method = RequestMethod.GET) : 무슨 방식으로 메소드를 호출할지
        GET 방식의 메소드로 "/api/test" 주소로 호출하면 getApiTest() 메소드를 호출할 수 있음

    @ResponseStatus(value = HttpStatus.OK)
        결과물에 대한 설정
        OK == 200
     */
}
