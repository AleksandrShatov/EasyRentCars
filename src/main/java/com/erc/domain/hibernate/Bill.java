package com.erc.domain.hibernate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.erc.domain.BillStatus;

@Entity
@Table(name = "bills")
@Data
@NoArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_price")
    private Integer totalPrice;

    @Column(name = "date_for_payment")
    private LocalDateTime dateForPayment;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column
    private Integer payment;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private BillStatus paymentStatus = BillStatus.AWAITING_PAYMENT;

    @OneToOne
    @JoinColumn(name = "rent_id", referencedColumnName = "id")
    private Rent rent;

}
