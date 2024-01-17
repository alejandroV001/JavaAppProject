package com.alejandro.app.rest.Services;
import com.alejandro.app.rest.Models.*;
import com.alejandro.app.rest.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class TripService {
    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TripUserRepository tripUserRepository;
    @Autowired
    private TripExpenseRepository tripExpenseRepository;
    @Autowired
    private ExpenseRepository expenseRepository;

    public void createTrip(Trip trip) {
        tripRepository.save(trip);
    }

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public void addMemberToTrip(long tripId, String email) {
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalTrip.isPresent() && optionalUser.isPresent()) {
            Trip trip = optionalTrip.get();
            User user = optionalUser.get();

            if (!isUserAlreadyMember(trip, user)) {
                TripUser tripUser = new TripUser();
                tripUser.setTrip(trip);
                tripUser.setUser(user);

                tripUserRepository.save(tripUser);
            } else {
                throw new RuntimeException("Utilizatorul este deja membru al călătoriei.");
            }
        } else {
            throw new RuntimeException("Călătoria sau utilizatorul nu există.");
        }
    }

    public void removeMemberFromTrip(long tripId, String email) {
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalTrip.isPresent() && optionalUser.isPresent()) {
            Trip trip = optionalTrip.get();
            User user = optionalUser.get();

            if (isUserAlreadyMember(trip, user)) {
                TripUser tripUser = tripUserRepository.findByTripAndUser(trip, user);

                tripUserRepository.delete(tripUser);
            }
        } else {
            throw new RuntimeException("Călătoria sau utilizatorul nu există.");
        }
    }


    private boolean isUserAlreadyMember(Trip trip, User user) {
        return tripUserRepository.findByTrip(trip).stream().anyMatch(tripUser -> tripUser.getUser().equals(user));
    }


}
