package com.erc.controller.rest;

import com.erc.controller.requests.DiscountCreateRequest;
import com.erc.controller.requests.DiscountUpdateRequest;
import com.erc.controller.requests.RentCreateRequest;
import com.erc.domain.hibernate.Car;
import com.erc.domain.hibernate.Discount;
import com.erc.domain.hibernate.Rent;
import com.erc.repository.hibernate.CarRepository;
import com.erc.repository.hibernate.DiscountRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/discount")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountRepository discountRepository;

    private final CarRepository carRepository;

    @ApiOperation("Find all discounts")
    @GetMapping("/find/all")
    public List<Discount> findAll() {
        return discountRepository.findAll();
    }

    @ApiOperation("Find discount by it's id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Discount ID", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/find/{id}") // TODO: 1
    public Discount findOne(@RequestParam Long id) {
        return discountRepository.findOne(id);
    }

    @ApiOperation("Find discounts by car id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "carId", value = "Car ID", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/find/{carId}") // TODO: 2
    public List<Discount> findByCarId(@RequestParam Long carId) {
        return discountRepository.findByCarId(carId);
    }

    @ApiOperation("Find discounts by percent")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "percent", value = "Discount percent", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/find/{percent}") // TODO: 3
    public List<Discount> findByPercent(@RequestParam Integer percent) {
        return discountRepository.findByPercent(percent);
    }

    // TODO: Don't work! Problem with converting data format.
    @ApiOperation("Find discounts from start date")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "Start date", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/find/{startDate}") // TODO: 4
    public List<Discount> findFromStartDate(@RequestParam LocalDateTime startDate) {
        return discountRepository.findFromStartDate(startDate);
    }

    @ApiOperation("Save new discount and return it")
    @PostMapping("/save/{request}")
    public Discount save(@RequestBody DiscountCreateRequest request) {

        try {
            Optional<Car> searchCarResult = Optional.ofNullable(carRepository.findOne(request.getCarId()));

            if(searchCarResult.isPresent()) {

                // TODO: Should check on Frontend?
//                if(request.getEndDate().isBefore(request.getStartDate())) {
//
//                }

                Car car = searchCarResult.get();
                // TODO: If no Data?!
                LocalDateTime lastEndTime = null;
                try {
                    lastEndTime = discountRepository.getLastEndDateByCarId(request.getCarId());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                if(lastEndTime == null ) {
                    lastEndTime = LocalDateTime.now();
                }

                // TODO: Don't work!
//                if(lastEndTime.isEqual(null)) {
//                    lastEndTime = LocalDateTime.now();
//                }

                if(car.getCarStatus().equals("AVAILABLE") && request.getStartDate().isAfter(lastEndTime)) {

                    Discount discount = new Discount();
                    discount.setCarForDiscount(car);
                    discount.setPercent(request.getPercent());
                    discount.setStartDate(request.getStartDate());
                    discount.setEndDate(request.getEndDate());

                    return discountRepository.save(discount);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // TODO: Fix null
        return null;
    }

    @ApiOperation("Save new discount")
    @PostMapping("/addone/{request}")
    public void addOne(@RequestBody DiscountCreateRequest request) {

        save(request);
    }

    @ApiOperation("Save list of discounts")
    @PostMapping("/save/{rents}")
    public void save(@RequestBody List<DiscountCreateRequest> discounts) {
        for (DiscountCreateRequest newDiscount : discounts) {
            save(newDiscount);
        }
    }

    @ApiOperation("Update discount data")
    @PostMapping("/update")
    public Discount update(@RequestBody DiscountUpdateRequest request) {
        // TODO: Need logic for check dates!
        try {
            Optional<Discount> searchDiscountRequest = Optional.ofNullable(discountRepository.findOne(request.getId()));
            Optional<Car> searchCarResult = Optional.ofNullable(carRepository.findOne(request.getCarId()));
            if (searchDiscountRequest.isPresent() && searchCarResult.isPresent()) {
                Car car = searchCarResult.get();

                Discount discount = new Discount();
                discount.setId(request.getId());
                discount.setPercent(request.getPercent());
                discount.setStartDate(request.getStartDate());
                discount.setEndDate(request.getEndDate());
                discount.setCarForDiscount(car);

                return discountRepository.update(discount);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // TODO: Fix null
        return null;
    }

    @ApiOperation("Hard delete discount by it's id ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Discount ID", required = true, dataType = "string", paramType = "query")
    })
    @DeleteMapping("/delete/{id}")
    public void delete(@RequestParam Long id) {

        discountRepository.delete(id);
    }

    @ApiOperation("Find last end date for current car id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "carId", value = "Car ID", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/find/last_end_date/{carId}")
    public LocalDateTime getLastEndDateByCarId(@RequestParam Long carId) {
        return discountRepository.getLastEndDateByCarId(carId);
    }
}
