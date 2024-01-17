package com.alejandro.app.rest.Services;

import com.alejandro.app.rest.Models.Trip;
import com.alejandro.app.rest.Models.TripUser;
import com.alejandro.app.rest.Models.User;
import com.alejandro.app.rest.Repository.TripRepository;
import com.alejandro.app.rest.Repository.UserRepository;
import com.alejandro.app.rest.Repository.TripUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TripUserRepository tripUserRepository;

    @InjectMocks
    private TripService tripService;

    @Test
    void testCreateTrip() {
        Trip trip = new Trip();
        tripService.createTrip(trip);
        verify(tripRepository, times(1)).save(trip);
    }

    @Test
    void testGetAllTrips() {
        when(tripRepository.findAll()).thenReturn(List.of(new Trip(), new Trip()));
        assertEquals(2, tripService.getAllTrips().size());
        verify(tripRepository, times(1)).findAll();
    }

//    @Test
//    void testAddMemberToTrip() {
//        long tripId = 1L;
//        String email = "user@example.com";
//
//        Trip trip = new Trip();
//        trip.setId(tripId);
//
//        User user = new User();
//        user.setEmail(email);
//
//        when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));
//        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
//        when(tripUserRepository.findByTripAndUser(trip, user)).thenReturn(null);
//
//        assertDoesNotThrow(() -> tripService.addMemberToTrip(tripId, email));
//
//        verify(tripUserRepository, times(1)).save(any(TripUser.class));
//    }

//    @Test
//    void testRemoveMemberFromTrip() {
//        long tripId = 1L;
//        String email = "user@example.com";
//
//        Trip trip = new Trip();
//        trip.setId(tripId);
//
//        User user = new User();
//        user.setEmail(email);
//
//        TripUser tripUser = new TripUser();
//        tripUser.setTrip(trip);
//        tripUser.setUser(user);
//
//        when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));
//        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
//        when(tripUserRepository.findByTripAndUser(trip, user)).thenReturn(tripUser);
//
//        assertDoesNotThrow(() -> tripService.removeMemberFromTrip(tripId, email));
//
//        verify(tripUserRepository, times(1)).findByTripAndUser(trip, user);
//        verify(tripUserRepository, times(1)).delete(tripUser);
//    }

    @Test
    void testRemoveMemberFromTripNonExistentUser() {
        long tripId = 1L;
        String email = "user@example.com";

        Trip trip = new Trip();
        trip.setId(tripId);

        User user = new User();
        user.setEmail(email);

        when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> tripService.removeMemberFromTrip(tripId, email));

        verify(tripRepository, times(1)).findById(tripId);
        verify(userRepository, times(1)).findByEmail(email);
        verify(tripUserRepository, never()).findByTripAndUser(any(), any());
        verify(tripUserRepository, never()).delete(any());
    }

    @Test
    void testRemoveMemberFromTripNonExistentTrip() {
        long tripId = 1L;
        String email = "user@example.com";

        when(tripRepository.findById(tripId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> tripService.removeMemberFromTrip(tripId, email));

        verify(tripUserRepository, never()).findByTripAndUser(any(), any());
        verify(tripUserRepository, never()).delete(any());
    }

}
