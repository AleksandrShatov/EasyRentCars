package com.erc.controller.requests;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiOperation(value = "Class for updating car entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarUpdateRequest {

    private Long id;

    private String regNumber;

    private LocalDateTime productionDate;

    private Integer tariff;

    private String color;

    private Long modelId;
}
