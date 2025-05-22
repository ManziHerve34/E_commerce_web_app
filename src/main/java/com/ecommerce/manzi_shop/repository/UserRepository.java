package com.ecommerce.manzi_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.manzi_shop.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);

    Optional<User> findByResetToken(String resetToken);

}


