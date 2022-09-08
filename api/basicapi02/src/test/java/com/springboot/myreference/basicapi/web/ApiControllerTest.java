package com.springboot.myreference.basicapi.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ApiController.class)
public class ApiControllerTest {

    String result = "{\"result\":\"ok\"}";

    @Autowired
    private MockMvc mvc;

    @Test
    public void commonGetTest() throws Exception { //tests passed
        //테스트할 get api 경로(실제 애플리케이션(Controller)에서 만든 경로)
        mvc.perform(get("/api/test"))
                //기대하는 응답 코드
                .andExpect(status().isOk())
                //기대하는 결과값
                .andExpect(content().string(result))
                //결과 출력
                .andDo(print());
    }
}
