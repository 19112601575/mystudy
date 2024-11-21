package com.example.exceptiontt.propertyT;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PropertyEntity {
    @NotBlank(message = "{test.person.name.NotBlank}")
    private String name;

    private Integer age;
}
