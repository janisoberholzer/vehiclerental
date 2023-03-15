package com.cs.springbootjavafx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "rentals")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "registertime")
    private Instant registertime;

    @Column(name = "pickuptime")
    private Instant pickuptime;

    @Column(name = "dropofftime")
    private Instant dropofftime;

    @Column(name = "price", precision = 10)
    private BigDecimal price;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle", nullable = false)
    private Vehicle vehicle;

    public Rental(Instant registertime, Instant pickuptime, Instant dropofftime, BigDecimal price, User user, Vehicle vehicle) {
        this.registertime = registertime;
        this.pickuptime = pickuptime;
        this.dropofftime = dropofftime;
        this.price = price;
        this.user = user;
        this.vehicle = vehicle;
    }

    public Rental() {
    }

    public String getVehicle() {
        return vehicle.getDescription();
    }
}