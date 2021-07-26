package com.erc.controller.requests;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiOperation(value = "Class for creating model entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelCreateRequest {

    private String manufacturer;

    private String modelName;

    private String fuel;

    private Float engineVolume;
}
