package com.alejandro.app.rest.Controllers;

import com.alejandro.app.rest.Models.Trip;
import com.alejandro.app.rest.Models.TripUser;
import com.alejandro.app.rest.Models.User;
import com.alejandro.app.rest.Services.ExpenseService;
import com.alejandro.app.rest.Services.TripService;
import com.alejandro.app.rest.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trips")
public class TripController {
    @Autowired
    private TripService tripService;
    @Autowired
    private UserService userService;
    @Autowired
    private ExpenseService expenseService;
    @PostMapping("/create")
    public ResponseEntity<String> createTrip(@Valid @RequestBody Trip trip) {
        try {
            String adminEmail = trip.getAdmin().getEmail();
            Optional<User> adminUserOptional = Optional.ofNullable(userService.getUserByEmail(adminEmail));

            if (adminUserOptional.isPresent()) {
                trip.setAdmin(adminUserOptional.get());
            } else {
                // Handle the case where the user with the provided email is not found
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Admin user with email " + adminEmail + " not found.");
            }
            tripService.createTrip(trip);
            return ResponseEntity.ok("Trip created successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Trip>> getAllTrips() {
        return ResponseEntity.ok(tripService.getAllTrips());
    }

    @PostMapping("/addMember")
    public ResponseEntity<String> addMemberToTrip(@Valid @RequestBody TripUser tripUser) {
        try {
            tripService.addMemberToTrip(tripUser.getTrip().getId(), tripUser.getUser().getEmail());
            return ResponseEntity.ok("Member added to trip successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/removeMember")
    public ResponseEntity<String> removeMemberFromTrip(@Valid @RequestBody TripUser tripUser) {
        try {
            tripService.removeMemberFromTrip(tripUser.getTrip().getId(), tripUser.getUser().getEmail());
            return ResponseEntity.ok("Member removed from trip successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}