package com.erc.repository.hibernate;

import com.erc.domain.hibernate.Car;
import com.erc.repository.CrudOperations;

import java.util.List;

public interface CarRepository extends CrudOperations<Long, Car> {

    Car findByRegNumber(String regNumber);

    List<Car> findByTariff(Integer tariff);

    List<Car> findByCarStatus(String carStatus);
}
