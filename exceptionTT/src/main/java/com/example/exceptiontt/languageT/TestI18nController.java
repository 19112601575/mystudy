package com.example.exceptiontt.languageT;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestI18nController {

    /**
     * 测试国际化
     * en_US 英文  http://127.0.0.1:8033/en_US
     * zh_CN 中文 http://127.0.0.1:8033/zh_CN
     */
    @GetMapping("/lang")
    public String test(@PathVariable String language) {
        String text1 = MessageUtil.get("test", language);
        String text2 = MessageUtil.get("test", new Object[]{language}, language);
        return new StringBuilder("没有替换占位符参数:")
                .append(text1)
                .append("<br/>")
                .append("替换占位符参数后:")
                .append(text2)
                .toString();
    }
}    
