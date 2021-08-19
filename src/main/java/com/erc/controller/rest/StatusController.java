package com.erc.controller.rest;

import com.erc.service.StatusService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @ApiOperation("Update status for all entities in project using current date")
    @PutMapping("update/status")
    public void updateStatusForAllEntities() {
        statusService.updateStatusForAllEntities();
    }


}
