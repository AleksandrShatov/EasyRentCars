package com.erc.domain.hibernate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private String paymentStatus;

    @OneToOne
    @JoinColumn(name = "rent_id")
    @JsonBackReference
    private Rent rent;

}