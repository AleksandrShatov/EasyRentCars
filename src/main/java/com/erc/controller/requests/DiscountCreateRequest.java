package com.erc.controller.requests;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiOperation(value = "Class for creating discount entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCreateRequest {

    private Integer percent;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long carId;

}
