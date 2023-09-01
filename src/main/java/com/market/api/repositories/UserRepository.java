package com.market.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.market.api.models.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>  {
    Optional<UserEntity> findByUsername(String userName);
    Boolean existsByUsername(String userName);
}
