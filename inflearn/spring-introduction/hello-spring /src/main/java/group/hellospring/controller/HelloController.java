package group.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model) { // mvc 예시
        model.addAttribute("data", "Spring");
        return "hello"; // resources:templates/ + {ViewName} + .html
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name",required = false) String name, Model model) { // 외부에서 파라미터를 받는 방식
        model.addAttribute("name", name);
        return "hello-template";
    }
}
