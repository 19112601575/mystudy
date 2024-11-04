package com.example.exceptiontt.key2;

import com.example.exceptiontt.key2.entity.License;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceT {

    @PostMapping("/dd")
    public void license(@RequestBody License license){

    }
}
