package com.erc.controller.requests;

import java.time.LocalDateTime;

import com.erc.domain.RentStatus;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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