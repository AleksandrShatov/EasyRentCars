package com.erc.controller.requests;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
