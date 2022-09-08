package com.springboot.myreference.basicapi.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 컨트롤러를 JSON 반환하는 컨트롤러로 만들어줌
// @ResponseBody` 를 각 메소드 마다 선언했던 것을 한번에 사용 할 수 있게 해줌
@RestController
public class HelloController {

    /*
    HTTP Method 인 Get 의 요청을 받을 수 있는 API 를 만들어줌
    예전에는 @RequestMapping(method = RequestMethod.GET)으로 사용되었습니다.
    이제 이프로젝트는 `/hello` 로 요청이 오면 문자열 “hello” 를 반환하는 기능을 가짐**
     */
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
