package com.erc.controller.requests;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiOperation(value = "Class for updating discount entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountUpdateRequest {

    private Long id;

    private Integer percent;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long carId;
}
