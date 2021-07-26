package com.erc.controller.requests;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiOperation("Class for creating role entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleUpdateRequest {
    private Integer id;
    private String roleName;
}