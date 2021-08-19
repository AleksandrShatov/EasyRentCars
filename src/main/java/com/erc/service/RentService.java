package com.erc.service;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.erc.domain.BillStatus;
import com.erc.domain.RentStatus;
import com.erc.domain.hibernate.Bill;
import com.erc.domain.hibernate.Rent;
import com.erc.repository.hibernate.BillRepository;
import com.erc.repository.hibernate.RentRepository;

@Service
@RequiredArgsConstructor
public class RentService {

    private final RentRepository rentRepository;

    private final BillRepository billRepository;

    public Rent confirmRent(Long rentId) {
        try {

            Optional<Rent> searchRentResult = Optional.ofNullable(rentRepository.findOne(rentId));

            if (searchRentResult.isPresent()) {

                Rent rent = searchRentResult.get();

                rentRepository.changeRentStatus(rentId, RentStatus.CONFIRMED);

                Integer tariff = rent.getCar().getTariff();
                Integer numberOfDays = rent.getNumberOfDays();
                Integer totalPrice = tariff * numberOfDays;
                LocalDateTime dateForPayment = rent.getStartRentDate().minusDays(1);

                Bill bill = new Bill();
                bill.setTotalPrice(totalPrice);
                bill.setDateForPayment(dateForPayment);
                bill.setPaymentStatus(BillStatus.AWAITING_PAYMENT);
                bill.setRent(rent);

                billRepository.addOne(bill);

                return rentRepository.findOne(rentId);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // TODO: Fix null
        return null;
    }
}
