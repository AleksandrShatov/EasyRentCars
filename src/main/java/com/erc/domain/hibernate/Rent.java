package com.erc.domain.hibernate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rents")
@Data
@NoArgsConstructor
//@EqualsAndHashCode(exclude = {"user", "car"})
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
    private String rentStatus;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties("rents")
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    @JsonIgnoreProperties("rents")
    private Car car;

    @OneToOne(mappedBy = "rent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Bill bill;
}
