package com.cs.springbootjavafx.repository;

import com.cs.springbootjavafx.entity.Rental;
import com.cs.springbootjavafx.entity.User;
import com.cs.springbootjavafx.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Integer> {

    @Query(value = "SELECT t.vehicle FROM Rental t where t.user.id = ?1 group by t.vehicle order by count(t.vehicle) desc limit 3")
    List<Vehicle> findPopular(int userid);

    List<Rental> findAllByVehicle(Vehicle vehicle);

    List<Rental> findAllByUser(User user);

}