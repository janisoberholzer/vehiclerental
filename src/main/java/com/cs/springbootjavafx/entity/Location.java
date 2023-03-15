package com.cs.springbootjavafx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "zip")
    private Integer zip;

    @Size(max = 45)
    @Column(name = "city", length = 45)
    private String city;

}