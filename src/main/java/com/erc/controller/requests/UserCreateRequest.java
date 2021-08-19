package com.erc.controller.requests;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiOperation(value = "Class for creating user entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {

    private String name;

    private String surname;

    private String patronymic;

    private Integer passportNumber;

    private String passportSeries;

    private Integer phone;

    private String email;

    private LocalDateTime birthDate;

    private String login;

    private String password;

}
