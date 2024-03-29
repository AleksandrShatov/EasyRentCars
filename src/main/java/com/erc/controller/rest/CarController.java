package com.erc.controller.rest;

import java.util.List;
import java.util.Optional;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.erc.controller.requests.CarCreateRequest;
import com.erc.controller.requests.CarUpdateRequest;
import com.erc.domain.CarStatus;
import com.erc.domain.hibernate.Car;
import com.erc.domain.hibernate.Model;
import com.erc.repository.hibernate.CarRepository;
import com.erc.repository.hibernate.ModelRepository;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {

    private final CarRepository carRepository;

    private final ModelRepository modelRepository;

    @ApiOperation("Find all cars")
    @GetMapping("find/all")
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @ApiOperation("Find car by it's id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Car ID", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("find/{id}")
    public Car findOne(@RequestParam Long id) {
        return carRepository.findOne(id);
    }

    @ApiOperation("Find car by it's reg_number")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "regNumber", value = "Car reg_number", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("find/{regNumber}")
    public Car findByRegNumber(@RequestParam String regNumber) {
        return carRepository.findByRegNumber(regNumber);
    }

    @ApiOperation("Find cars by it's tariff")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tariff", value = "Car tariff", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("find/{tariff}")
    public List<Car> findByTariff(@RequestParam Integer tariff) {
        return carRepository.findByTariff(tariff);
    }

    @ApiOperation("Find cars by it's status")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "carStatus", value = "Car status", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("find/{carStatus}")
    public List<Car> findByCarStatus(@RequestParam CarStatus carStatus) {
        return carRepository.findByCarStatus(carStatus);
    }

    @ApiOperation("Find available cars (with car status = 'AVAILABLE')")
    @GetMapping("find/availableCars")
    public List<Car> findAvailableCars() {
        return carRepository.findByCarStatus(CarStatus.AVAILABLE);
    }

    @ApiOperation("Save new car and return it")
    @PostMapping("save/one")
    public Car save(@RequestBody CarCreateRequest request) {

        try {
            Optional<Model> searchResult = Optional.ofNullable(modelRepository.findOne(request.getModelId()));
            if (searchResult.isPresent()) {
                Model model = searchResult.get();

                Car car = new Car();
                car.setRegNumber(request.getRegNumber());
                car.setProductionDate(request.getProductionDate());
                car.setTariff(request.getTariff());
                car.setColor(request.getColor());
                car.setModel(model);

                return carRepository.save(car);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // TODO: Fix null
        return null;
    }

    @ApiOperation("Save new car")
    @PostMapping("/add/one")
    public void addOne(@RequestBody CarCreateRequest request) {

        save(request);
    }

    @ApiOperation("Save list of cars")
    @PostMapping("save/many")
    public void save(@RequestBody List<CarCreateRequest> cars) {
        for (CarCreateRequest newCar : cars) {
            save(newCar);
        }
    }

    @ApiOperation("Update car data")
    @PutMapping("/update")
    public Car update(@RequestBody CarUpdateRequest request) {
        try {
            Optional<Model> searchModelResult = Optional.ofNullable(modelRepository.findOne(request.getModelId()));
            Optional<Car> searchCarResult = Optional.ofNullable(carRepository.findOne(request.getId()));
            if (searchModelResult.isPresent() && searchCarResult.isPresent()) {
                Model model = searchModelResult.get();

                Car car = new Car();
                car.setId(request.getId());
                car.setRegNumber(request.getRegNumber());
                car.setProductionDate(request.getProductionDate());
                car.setTariff(request.getTariff());
                car.setColor(request.getColor());
                car.setModel(model);

                return carRepository.update(car);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // TODO: Fix null
        return null;
    }

    @ApiOperation("Hard delete car by it's id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Car ID", required = true, dataType = "string", paramType = "query")
    })
    @DeleteMapping("delete/{id}")
    public void delete(@RequestParam Long id) {
        carRepository.delete(id);
    }

    @ApiOperation("Change car status")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "carId", value = "Car ID", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "carStatus", value = "New status for car", required = true, dataType = "string", paramType = "query",
                    allowableValues = "NOT_AVAILABLE, AVAILABLE, RESERVED, IN_RENT")
    })
    @PutMapping("/change/carStatus/{carId, carStatus}")
    public Car changeCarStatus(@RequestParam Long carId, @RequestParam CarStatus carStatus) {

        carRepository.changeCarStatus(carId, carStatus);

        return findOne(carId);
    }

}
