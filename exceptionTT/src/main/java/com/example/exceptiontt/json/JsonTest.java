package com.example.exceptiontt.json;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*
    测试实体类驼峰命名，@Data自动生成getter时，接收不到json数据
 */
@RestController
public class JsonTest {

    @RequestMapping("/jsontest")
    public void jsonTest(@RequestBody JsonCat jsonCat){
        JsonCat jsonCat1 = new JsonCat();
        jsonCat1.setCatColor(jsonCat.getCatColor());
        jsonCat1.setCatName(jsonCat.getCatName());
        System.out.println(jsonCat1);
    }
}
