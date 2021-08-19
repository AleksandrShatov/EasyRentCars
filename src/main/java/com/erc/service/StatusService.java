package com.erc.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.erc.domain.BillStatus;
import com.erc.domain.CarStatus;
import com.erc.domain.RentStatus;
import com.erc.domain.hibernate.Bill;
import com.erc.repository.hibernate.BillRepository;
import com.erc.repository.hibernate.CarRepository;
import com.erc.repository.hibernate.RentRepository;


@Service
@RequiredArgsConstructor
public class StatusService {

    private final CarRepository carRepository;

    private final RentRepository rentRepository;

    private final BillRepository billRepository;

    public void updateStatusForAllEntities() {

        List<Bill> billsWithExpiredDates = billRepository.findWithoutPaymentAndExpiredDates();

        for(Bill bill : billsWithExpiredDates) {
            billRepository.changeBillPaymentStatus(bill.getId(), BillStatus.EXPIRED);
            rentRepository.changeRentStatus(bill.getRent().getId(), RentStatus.CANCELED);
            carRepository.changeCarStatus(bill.getRent().getCar().getId(), CarStatus.AVAILABLE);
        }
    }



}
