package com.example.exceptiontt.propertyT;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.NotBlank;


@Data
public class PropertyEntity {
    @NotBlank(message = "{test.person.name.NotBlank}")
    private String name;

    private Integer age;
}
