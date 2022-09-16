package group.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model) { // mvc 예시
        model.addAttribute("data", "Spring");
        return "hello"; // resources:templates/ + {ViewName} + .html
    }

    @GetMapping("hello-mvc") // 템플릿 엔진 사용
    public String helloMvc(@RequestParam(value = "name", required = false) String name, Model model) { // 외부에서 파라미터를 받는 방식
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string") // api 방식 예시(데이터만 전송)
    @ResponseBody // HTTP body부에 return 데이터를 직접 넣어주겠음
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // hello spring
    }
}
