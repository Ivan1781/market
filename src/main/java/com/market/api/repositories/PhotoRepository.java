package com.market.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.market.api.models.Photo;


public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Optional<Photo> findById (Long id);
}
