package com.erc.domain.hibernate;

import com.erc.domain.CarStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"rents", "discounts", "model"})
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reg_number")
    private String regNumber;

    @Column(name = "production_date")
    private LocalDateTime productionDate;

    @Column
    private Integer tariff;

    @Column
    private String color;

    @Column(name = "car_status")
    @Enumerated(EnumType.STRING)
    private CarStatus carStatus = CarStatus.NOT_AVAILABLE;

    @ToString.Exclude // TODO: Was endless loop between car and model, after adding Discount mapping
    @ManyToOne
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    @JsonBackReference
    private Model model;

    @ToString.Exclude
    @OneToMany(mappedBy = "carForDiscount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Discount> discounts = Collections.emptySet();

    @ToString.Exclude
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Rent> rents = Collections.emptySet();
}
