package com.erc.controller.requests;

import com.erc.domain.RentStatus;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiOperation(value = "Class for updating rent entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentUpdateRequest {

    private Long id;

    private Long userId;

    private Long carId;

    private LocalDateTime startRentDate;

    private LocalDateTime endRentDate;

    private LocalDateTime actualReturnDate;

    private Integer numberOfDays;

    private RentStatus rentStatus;

}