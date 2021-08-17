package com.erc.domain.hibernate;

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
@Table(name = "users")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"roles", "rents"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String patronymic;

    @Column(name = "passport_number")
    private Integer passportNumber;

    @Column(name = "passport_series")
    private String passportSeries;

    @Column
    private Integer phone;

    @Column
    private String email;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @Column
    private String login;

    @Column
    private String password;

    @ToString.Exclude
    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Role> roles = Collections.emptySet();

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Rent> rents = Collections.emptySet();

}
