package com.erc.domain.hibernate;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "models")
@Data
@NoArgsConstructor

public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column
    private String manufacturer;

    @Column
    private String model;

    @Column
    private String fuel;

    @Column(name = "engine_volume")
    private String engineVolume;

}
