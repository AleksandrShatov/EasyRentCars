package com.erc.controller.rest;

import com.erc.controller.requests.BillCreateRequest;
import com.erc.controller.requests.BillUpdateRequest;
import com.erc.domain.BillStatus;
import com.erc.domain.RentStatus;
import com.erc.domain.hibernate.Bill;
import com.erc.domain.hibernate.Rent;
import com.erc.repository.hibernate.BillRepository;
import com.erc.repository.hibernate.RentRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bill")
@RequiredArgsConstructor
public class BillController {

    private final BillRepository billRepository;

    private final RentRepository rentRepository;

    @ApiOperation("Find all bills")
    @GetMapping("/find/all")
    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    @ApiOperation("Find bill by it's id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Bill ID", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/find/{id}")
    public Bill findOne(@RequestParam Long id) {
        return billRepository.findOne(id);
    }

    @ApiOperation("Find bill by rent id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rentId", value = "Rent ID", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/find/{rentId}")
    public Bill findByRentId(Long rentId) {
        return billRepository.findByRentId(rentId);
    }

    @ApiOperation("Find bills by user id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "User ID", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/find/{userId}")
    public List<Bill> findByUserId(Long userId) {
        return billRepository.findByUserId(userId);
    }

    @ApiOperation("Find bills by car id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "carId", value = "Car ID", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/find/{carId}")
    public List<Bill> findByCarId(Long carId) {
        return billRepository.findByCarId(carId);
    }

    @ApiOperation("Find bills by payment status")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paymentStatus", value = "Payment status", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/find/{paymentStatus}")
    public List<Bill> findByPaymentStatus(BillStatus paymentStatus) {
        return billRepository.findByPaymentStatus(paymentStatus);
    }

    @ApiOperation("Find bills without payment and expired dates")
    @GetMapping("/find/withoutPayment")
    public List<Bill> findWithoutPaymentAndExpiredDates() {
        return billRepository.findWithoutPaymentAndExpiredDates();
    }

    @ApiOperation("Save new bill and return it.")
    @PostMapping("/save/{request}")
    public Bill save(@RequestBody BillCreateRequest request) {

        try {
            Optional<Rent> searchRentResult = Optional.ofNullable(rentRepository.findOne(request.getRentId()));

            if(searchRentResult.isPresent()) {

                Rent rent = searchRentResult.get();

                if(rent.getRentStatus().equals("CONFIRMED")) {

                    Bill bill = new Bill();
                    bill.setTotalPrice(request.getTotalPrice());
                    bill.setDateForPayment(request.getDateForPayment());
                    bill.setPaymentStatus(BillStatus.AWAITING_PAYMENT);
                    bill.setRent(rent);

                    return billRepository.save(bill);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // TODO: Fix null
        return null;
    }

    @ApiOperation("Save new bill")
    @PostMapping("/addone/{request}")
    public void addOne(@RequestBody BillCreateRequest request) {

        save(request);
    }

    @ApiOperation("Save list of bills")
    @PostMapping("/save/{rents}")
    public void save(@RequestBody List<BillCreateRequest> bills) {
        for (BillCreateRequest newBill : bills) {
            save(newBill);
        }
    }

    @ApiOperation("Update bill data, only if rent the same or new rent status is 'CONFIRMED'")
    @PostMapping("/update")
    public Bill update(@RequestBody BillUpdateRequest request) {
        try {

            Optional<Bill> searchBillRequest = Optional.ofNullable(billRepository.findOne(request.getId()));
            Optional<Rent> searchRentRequest = Optional.ofNullable(rentRepository.findOne(request.getRentId()));

            if(searchBillRequest.isPresent() && searchRentRequest.isPresent()) {

                Rent rent = searchRentRequest.get();
                Bill bill = searchBillRequest.get();

                boolean isRentEquals = bill.getRent().getId().equals(rent.getId());
                boolean isRentConfirmed = rent.getRentStatus().equals(RentStatus.CONFIRMED);

                if(isRentEquals || isRentConfirmed) {

                    bill.setId(request.getId());
                    bill.setTotalPrice(request.getTotalPrice());
                    bill.setDateForPayment(request.getDateForPayment());
                    bill.setPaymentStatus(request.getPaymentStatus());
                    bill.setPaymentDate(request.getPaymentDate());
                    bill.setPayment(request.getPayment());
                    bill.setRent(rent);

                    return billRepository.update(bill);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // TODO: Fix null
        return null;
    }

    @ApiOperation("Hard delete bill by it's id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Bill ID", required = true, dataType = "string", paramType = "query")
    })
    @DeleteMapping("/delete/{id}")
    public void delete(@RequestParam Long id) {

        try {

            Optional<Bill> searchBillRequest = Optional.ofNullable(billRepository.findOne(id));

            if(searchBillRequest.isPresent()) {

                billRepository.delete(id);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @ApiOperation("Change bill status")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "billId", value = "Bill ID", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "paymentStatus", value = "New status for bill", required = true, dataType = "string", paramType = "query",
                    allowableValues = "AWAITING_PAYMENT, PAID, EXPIRED, CANCELED")
    })
    @PostMapping("/change/billStatus/{billId, paymentStatus}")
    public void changeBillPaymentStatus(@RequestParam Long billId, BillStatus paymentStatus) {
        billRepository.changeBillPaymentStatus(billId, paymentStatus);
    }

    @ApiOperation("Pay for bill")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "billId", value = "Bill ID", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "payment", value = "Invoice payment amount", required = true, dataType = "string", paramType = "query")
    })
    @PostMapping("/payment/{billId, payment}")
    public Bill paymentByBill(Long billId, Integer payment) {

        try {

            Optional<Bill> searchBillRequest = Optional.ofNullable(billRepository.findOne(billId));

            if(searchBillRequest.isPresent()) {

                Bill bill = searchBillRequest.get();
                Integer totalPrice = bill.getTotalPrice();

                if(payment.equals(totalPrice)) {
                    LocalDateTime paymentDate = LocalDateTime.now();
                    billRepository.paymentByBill(billId, payment , paymentDate);
                    billRepository.changeBillPaymentStatus(billId, BillStatus.PAID);

                    return billRepository.findOne(billId);
                }
            }
        }  catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // TODO: Fix null
        return null;

    }

}
