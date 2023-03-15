package com.cs.springbootjavafx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 145)
    @Column(name = "description", length = 145)
    private String description;

    @Column(name = "year")
    private Integer year;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category", nullable = false)
    private Category category;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location", nullable = false)
    private Location location;

    public String getCategory() {
        return category.getName();
    }

    public String getLocation() {
        return location.getAddress();
    }
    public Category getCategoryObject(){
        return category;
    }
}