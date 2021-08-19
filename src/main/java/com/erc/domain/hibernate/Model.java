package com.erc.domain.hibernate;

import java.util.Collections;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "models")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"cars"})
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String manufacturer;

    @Column(name = "model_name")
    private String modelName;

    @Column
    private String fuel;

    @Column(name = "engine_volume")
    private Float engineVolume;

    @ToString.Exclude
    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Car> cars = Collections.emptySet();
}
