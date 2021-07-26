package com.erc.controller.requests;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiOperation(value = "Class for updating model entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelUpdateRequest {

    private Long id;

    private String manufacturer;

    private String modelName;

    private String fuel;

    private Float engineVolume;
}
