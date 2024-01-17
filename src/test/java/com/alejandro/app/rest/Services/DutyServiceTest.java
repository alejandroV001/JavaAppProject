package com.alejandro.app.rest.Services;

import com.alejandro.app.rest.Models.Duty;
import com.alejandro.app.rest.Models.User;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.alejandro.app.rest.Models.Duty;
import com.alejandro.app.rest.Models.User;
import com.alejandro.app.rest.Repository.DutyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DutyServiceTest {

    @Autowired
    private DutyService dutyService;

    @MockBean
    private DutyRepository dutyRepository;

    @Test
    void testCreateDuty() {
        Duty duty = new Duty();
        dutyService.createDuty(duty);

        verify(dutyRepository, times(1)).save(duty);
    }

    @Test
    void testGetAllDebtsForUser() {
        User user = new User();
        List<Duty> debts = new ArrayList<>();
        debts.add(new Duty());

        when(dutyRepository.findByDebtor(user)).thenReturn(debts);

        List<Duty> result = dutyService.getAllDebtsForUser(user);

        assertEquals(debts, result);
    }

    @Test
    void testGetAllCreditsForUser() {
        User user = new User();
        List<Duty> credits = new ArrayList<>();
        credits.add(new Duty());

        when(dutyRepository.findByCreditor(user)).thenReturn(credits);

        List<Duty> result = dutyService.getAllCreditsForUser(user);

        assertEquals(credits, result);
    }

    @Test
    void testGetAllDebtsAndCreditsForUser() {
        User user = new User();
        List<Duty> debtsAsDebtor = new ArrayList<>();
        List<Duty> debtsAsCreditor = new ArrayList<>();
        debtsAsDebtor.add(new Duty());
        debtsAsCreditor.add(new Duty());

        when(dutyRepository.findByDebtor(user)).thenReturn(debtsAsDebtor);
        when(dutyRepository.findByCreditor(user)).thenReturn(debtsAsCreditor);

        List<Duty> result = dutyService.getAllDebtsAndCreditsForUser(user);

        assertEquals(debtsAsDebtor.size() , result.size());
    }

}