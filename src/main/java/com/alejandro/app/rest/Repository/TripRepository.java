package com.alejandro.app.rest.Repository;

import com.alejandro.app.rest.Models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
