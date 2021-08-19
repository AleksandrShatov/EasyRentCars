package com.erc.repository.hibernate;

import java.util.List;

import com.erc.domain.RentStatus;
import com.erc.domain.hibernate.Rent;
import com.erc.repository.CrudOperations;

public interface RentRepository extends CrudOperations<Long, Rent> {

    List<Rent> findByUserId(Long userId);

    List<Rent> findByCarId(Long carId);

    void changeRentStatus(Long rentId, RentStatus rentStatus);

}
