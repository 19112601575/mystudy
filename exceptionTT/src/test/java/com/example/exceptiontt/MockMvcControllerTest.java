package com.example.exceptiontt;

import com.example.exceptiontt.lambda.Person;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
public class MockMvcControllerTest {
//    private MockMvc mockMvc;
//
//    private HttpHeaders headers;
//    @BeforeEach
//    public void setup(){
//        mockMvc = MockMvcBuilders.standaloneSetup(new Person()).build();
//        // 实例化方式二
////        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }
//
//    @Test
//    public void Test1() throws Exception {
//        //读取json文件数据
//        String str = FileUtils.readFileToString(new File(getClass().getResource("com/example/exceptiontt/mock/personTest.json").getPath()),"UTF-8");
//        JSONObject jsonObject = (JSONObject) JSONValue.parse(str);
//        Person person = new Person();
//        person.setAge(22);
//        //所期望的返回数据及形式
//        String s = "success";
//        headers = new HttpHeaders();
//        mockMvc.perform(MockMvcRequestBuilders.post("/mock/test").headers(headers).content(person.toString()))
//                //期望做出的操作：输出
//                .andDo(print())
//                //所期待的code值是否相同，不同报错
////                .andExpect(jsonPath("$.code").value("200"))
//                .andExpect(jsonPath("$.msg").value(s))
//                .andExpect(jsonPath("$.data").value(jsonObject));
//    }
}
