package com.erc.repository.hibernate;

import com.erc.domain.hibernate.Discount;
import com.erc.repository.CrudOperations;

import java.time.LocalDateTime;
import java.util.List;

public interface DiscountRepository extends CrudOperations<Long, Discount> {

    List<Discount> findByCarId(Long carId);

    List<Discount> findByPercent(Integer percent);

    List<Discount> findFromStartDate(LocalDateTime startDate);

}
