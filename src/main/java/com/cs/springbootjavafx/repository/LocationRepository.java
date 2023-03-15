package com.cs.springbootjavafx.repository;

import com.cs.springbootjavafx.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    Location findLocationByAddress(String Address);
}