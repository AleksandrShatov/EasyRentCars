package com.erc.controller.requests;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiOperation(value = "Class for paying by bill")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillPaymentRequest {

    private Long id;

    private LocalDateTime paymentDate;

    private Integer payment;

}
