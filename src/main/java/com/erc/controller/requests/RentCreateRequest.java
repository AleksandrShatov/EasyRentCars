package com.erc.controller.requests;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiOperation(value = "Class for creating rent entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentCreateRequest {

    private Long userId;

    private Long carId;

    private LocalDateTime startRentDate;

    private Integer numberOfDays;

}
