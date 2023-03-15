package com.cs.springbootjavafx.repository;

import com.cs.springbootjavafx.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(int Id);
    User findUserByEmailAndPassword(String Email, String Password);
}