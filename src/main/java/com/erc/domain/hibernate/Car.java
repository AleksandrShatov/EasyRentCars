package com.erc.domain.hibernate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"rents"})
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

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("car")
    private Set<Rent> rents = Collections.emptySet();
}
