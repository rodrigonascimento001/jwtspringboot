package com.example.jwt.authprojectjwt.repository;

import com.example.jwt.authprojectjwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
