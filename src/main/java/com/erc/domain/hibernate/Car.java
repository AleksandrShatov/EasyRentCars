package com.erc.domain.hibernate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
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
    private String carStatus;

    @ManyToOne
    @JoinColumn(name = "model_id", referencedColumnName = "id")
//    @JsonBackReference // Don't show model for cars!!!
    @JsonIgnoreProperties("cars")
    private Model model;

}
