package com.erc.controller.requests;

import com.erc.domain.BillStatus;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiOperation(value = "Class for updating bill entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillUpdateRequest {

    private Long id;

    private Long rentId;

    private Integer totalPrice;

    private LocalDateTime dateForPayment;

    private BillStatus paymentStatus;

    private LocalDateTime paymentDate;

    private Integer payment;

}