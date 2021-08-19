package com.erc.controller.requests;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiOperation(value = "Class for creating bill entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillCreateRequest {

    private Long rentId;

    private Integer totalPrice;

    private LocalDateTime dateForPayment;
    
}
