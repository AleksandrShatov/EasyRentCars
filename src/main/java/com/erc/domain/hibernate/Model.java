package com.erc.domain.hibernate;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "models")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"cars"})
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

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Car> cars = Collections.emptySet();

}
