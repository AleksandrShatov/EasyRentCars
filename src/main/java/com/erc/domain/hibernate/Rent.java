package com.erc.domain.hibernate;

import com.erc.domain.RentStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rents")
@Data
@NoArgsConstructor
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_rent_date")
    private LocalDateTime startRentDate;

    @Column(name = "number_of_days")
    private Integer numberOfDays;

    @Column(name = "end_rent_date")
    private LocalDateTime endRentDate;

    @Column(name = "actual_return_date")
    private LocalDateTime actualReturnDate;

    @Column(name = "rent_status")
    @Enumerated(EnumType.STRING)
    private RentStatus rentStatus = RentStatus.NOT_CONFIRMED;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    @JsonBackReference
    private Car car;

    // TODO: StackOverflowError with Bill and Rent
//    @ToString.Exclude
//    @OneToOne(mappedBy = "rent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JsonManagedReference
//    private Bill bill;
}
