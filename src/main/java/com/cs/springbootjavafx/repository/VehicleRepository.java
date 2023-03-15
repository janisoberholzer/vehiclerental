package com.cs.springbootjavafx.repository;

import com.cs.springbootjavafx.entity.Category;
import com.cs.springbootjavafx.entity.Location;
import com.cs.springbootjavafx.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    List<Vehicle> findAllByCategoryAndLocation(Category Category, Location Location);
}