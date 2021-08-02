package com.erc.controller.requests;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiOperation(value = "Class for creating bill entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillCreateRequest {

    private Long rentId;

    private Integer totalPrice;

    private LocalDateTime dateForPayment;

    private String paymentStatus;

}
