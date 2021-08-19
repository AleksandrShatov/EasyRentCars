package com.erc.repository.hibernate;

import java.util.List;

import com.erc.domain.CarStatus;
import com.erc.domain.hibernate.Car;
import com.erc.repository.CrudOperations;

public interface CarRepository extends CrudOperations<Long, Car> {

    Car findByRegNumber(String regNumber);

    List<Car> findByTariff(Integer tariff);

    List<Car> findByCarStatus(CarStatus carStatus);

    void changeCarStatus(Long carId, CarStatus carStatus);
}
