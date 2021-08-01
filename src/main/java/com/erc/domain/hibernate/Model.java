package com.erc.domain.hibernate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
//    @JsonManagedReference // Don't show model for cars!!!
    @JsonIgnoreProperties("model")
    private Set<Car> cars = Collections.emptySet();

}
