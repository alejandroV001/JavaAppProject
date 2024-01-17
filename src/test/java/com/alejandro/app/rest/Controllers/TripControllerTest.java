package com.alejandro.app.rest.Controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.alejandro.app.rest.Models.Trip;
import com.alejandro.app.rest.Models.TripUser;
import com.alejandro.app.rest.Models.User;
import com.alejandro.app.rest.Services.TripService;
import com.alejandro.app.rest.Services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class TripControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TripService tripService;

    @MockBean
    private UserService userService;

    @Test
    void testCreateTrip() throws Exception {
        Trip trip = new Trip();
        trip.setAdmin(new User());

        when(userService.getUserByEmail(anyString())).thenReturn(new User());
        doNothing().when(tripService).createTrip(any(Trip.class));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/trips/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"admin\": {\"email\": \"admin@example.com\"}}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(userService, times(1)).getUserByEmail("admin@example.com");
        verify(tripService, times(1)).createTrip(any(Trip.class));
    }

    @Test
    void testGetAllTrips() throws Exception {
        List<Trip> trips = new ArrayList<>();
        trips.add(new Trip());

        when(tripService.getAllTrips()).thenReturn(trips);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/trips/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertTrue(responseContent.contains("id"));
    }

    @Test
    void testAddMemberToTrip() throws Exception {
        TripUser tripUser = new TripUser();
        tripUser.setTrip(new Trip());
        tripUser.setUser(new User());

        doNothing().when(tripService).addMemberToTrip(anyLong(), anyString());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/trips/addMember")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"trip\": {\"id\": 1}, \"user\": {\"email\": \"user@example.com\"}}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(tripService, times(1)).addMemberToTrip(anyLong(), anyString());
    }

    @Test
    void testRemoveMemberFromTrip() throws Exception {
        TripUser tripUser = new TripUser();
        tripUser.setTrip(new Trip());
        tripUser.setUser(new User());

        doNothing().when(tripService).removeMemberFromTrip(anyLong(), anyString());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/trips/removeMember")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"trip\": {\"id\": 1}, \"user\": {\"email\": \"user@example.com\"}}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(tripService, times(1)).removeMemberFromTrip(anyLong(), anyString());
    }
}
