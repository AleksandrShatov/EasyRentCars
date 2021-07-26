package com.erc.domain.hibernate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "reg_number")
    private String regNumber;

    @Column(name = "production_date")
    private String productionDate;

    @Column
    private String tariff;

    @Column
    private String insurance;

    @Column
    private String type;

    @Column
    private String color;

    @Column(name = "car_status")
    private String carStatus;

    @ManyToOne
    @JoinColumn(name = "model_id")
    @JsonBackReference
    private Model model;

}
