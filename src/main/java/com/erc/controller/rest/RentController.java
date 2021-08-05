package com.erc.controller.rest;

import com.erc.controller.requests.RentCreateRequest;
import com.erc.controller.requests.RentUpdateRequest;
import com.erc.domain.CarStatus;
import com.erc.domain.RentStatus;
import com.erc.domain.hibernate.Car;
import com.erc.domain.hibernate.Rent;
import com.erc.domain.hibernate.User;
import com.erc.repository.hibernate.CarRepository;
import com.erc.repository.hibernate.RentRepository;
import com.erc.repository.hibernate.UserRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rent")
@RequiredArgsConstructor
public class RentController {

    private final RentRepository rentRepository;

    private final UserRepository userRepository;

    private final CarRepository carRepository;

    @ApiOperation("Find all rents")
    @GetMapping("/find/all")
    public List<Rent> findAll() {
        return rentRepository.findAll();
    }

    @ApiOperation("Find rent by it's id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Rent ID", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/find/{id}")
    public Rent findOne(@RequestParam Long id) {
        return rentRepository.findOne(id);
    }

    @ApiOperation("Find rents by user id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "User ID", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/find/{userId}")
    public List<Rent> findByUserId(@RequestParam Long userId) {
        return rentRepository.findByUserId(userId);
    }

    @ApiOperation("Find rents by car id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "carId", value = "Car ID", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/find/{carId}")
    public List<Rent> findByCarId(@RequestParam Long carId) {
        return rentRepository.findByCarId(carId);
    }

    @ApiOperation("Save new rent and return it, also change car status from 'AVAILABLE' to 'RESERVED'")
    @PostMapping("/save/{request}")
    public Rent save(@RequestBody RentCreateRequest request) {

        try {
            Optional<User> searchUserResult = Optional.ofNullable(userRepository.findOne(request.getUserId()));
            Optional<Car> searchCarResult = Optional.ofNullable(carRepository.findOne(request.getCarId()));

            if (searchUserResult.isPresent() && searchCarResult.isPresent()) {

                User user = searchUserResult.get();
                Car car = searchCarResult.get();

                if(car.getCarStatus().equals(CarStatus.AVAILABLE)) {

                    carRepository.changeCarStatus(car.getId(), CarStatus.RESERVED);

                    LocalDateTime startDate = request.getStartRentDate();
                    Integer numberOfDays = request.getNumberOfDays();
                    LocalDateTime endDate = startDate.plusDays(numberOfDays);

                    Rent rent = new Rent();
                    rent.setUser(user);
                    rent.setCar(car);
                    rent.setStartRentDate(startDate);
                    rent.setNumberOfDays(numberOfDays);
                    rent.setRentStatus(RentStatus.NOT_CONFIRMED);
                    rent.setEndRentDate(endDate);

                    return rentRepository.save(rent);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // TODO: Fix null
        return null;
    }

    @ApiOperation("Save new rent")
    @PostMapping("/addone/{request}")
    public void addOne(@RequestBody RentCreateRequest request) {

        save(request);
    }

    @ApiOperation("Save list of rents")
    @PostMapping("/save/{rents}")
    public void save(@RequestBody List<RentCreateRequest> rents) {
        for (RentCreateRequest newRent : rents) {
            save(newRent);
        }
    }

    @ApiOperation("Update rent data, only if car the same or new car status is 'AVAILABLE'")
    @PostMapping("/update")
    public Rent update(@RequestBody RentUpdateRequest request) {
        try {

            Optional<Rent> searchRentResult = Optional.ofNullable(rentRepository.findOne(request.getId()));
            Optional<User> searchUserResult = Optional.ofNullable(userRepository.findOne(request.getUserId()));
            Optional<Car> searchCarResult = Optional.ofNullable(carRepository.findOne(request.getCarId()));

            if (searchRentResult.isPresent() && searchUserResult.isPresent() && searchCarResult.isPresent()) {

                User user = searchUserResult.get();
                Car car = searchCarResult.get();
                Rent rent = searchRentResult.get();

                boolean isCarEquals = rent.getCar().getId().equals(car.getId());
                boolean isCarAvailable = car.getCarStatus().equals(CarStatus.AVAILABLE);

                if(isCarEquals || isCarAvailable) {

                    if(!isCarEquals) {
                        carRepository.changeCarStatus(rent.getCar().getId(), CarStatus.AVAILABLE);
                        carRepository.changeCarStatus(car.getId(), CarStatus.RESERVED);
                    }

                    rent.setId(request.getId());
                    rent.setStartRentDate(request.getStartRentDate());
                    rent.setNumberOfDays(request.getNumberOfDays());
                    rent.setEndRentDate(request.getEndRentDate());
                    rent.setActualReturnDate(request.getActualReturnDate());
                    rent.setRentStatus(request.getRentStatus());
                    rent.setUser(user);
                    rent.setCar(car);

                    return rentRepository.update(rent);
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // TODO: Fix null
        return null;
    }

    @ApiOperation("Hard delete rent by it's id and set car status to 'AVAILABLE'")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Rent ID", required = true, dataType = "string", paramType = "query")
    })
    @DeleteMapping("/delete/{id}")
    public void delete(@RequestParam Long id) {

        try {
            Optional<Rent> searchRentResult = Optional.ofNullable(rentRepository.findOne(id));

            if(searchRentResult.isPresent()) {

                Rent rent = searchRentResult.get();
                Long carId = rent.getCar().getId();
                carRepository.changeCarStatus(carId, CarStatus.AVAILABLE);
                rentRepository.delete(id);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @ApiOperation("Change rent status")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rentId", value = "Rent ID", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "rentStatus", value = "New status for rent", required = true, dataType = "string", paramType = "query",
                    allowableValues = "NOT_CONFIRMED, CONFIRMED, CANCELED")
    })
    @PostMapping("/change/rentStatus/{rentId, rentStatus}")
    public void changeRentStatus(Long rentId, RentStatus rentStatus) {
        rentRepository.changeRentStatus(rentId, rentStatus);
    }

}
