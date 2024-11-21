package com.example.exceptiontt.propertyT;

import com.example.exceptiontt.exception.Resp;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/Test")
public class PropertyController {
    @PostMapping("/property")
    @ResponseBody
    public Resp test(@Valid @RequestBody PropertyEntity propertyEntity){
        PropertyEntity pe = new PropertyEntity();
        pe.setName(propertyEntity.getName());
        pe.setAge(propertyEntity.getAge());
        return Resp.success(pe);
    }

}
