package com.erc.repository.hibernate;

import com.erc.domain.BillStatus;
import com.erc.domain.hibernate.Bill;
import com.erc.repository.CrudOperations;

import java.time.LocalDateTime;
import java.util.List;

public interface BillRepository extends CrudOperations<Long, Bill> {

    Bill findByRentId(Long rentId);

    List<Bill> findByUserId(Long userId);

    List<Bill> findByCarId(Long carId);

    List<Bill> findByPaymentStatus(BillStatus paymentStatus);

    List<Bill> findWithoutPaymentAndExpiredDates();

    void changeBillPaymentStatus(Long billId, BillStatus paymentStatus);

    Bill paymentByBill(Long billId, Integer payment, LocalDateTime paymentDate);

}
