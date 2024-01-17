package com.alejandro.app.rest.Services;

import com.alejandro.app.rest.Models.Expense;
import com.alejandro.app.rest.Repository.ExpenseRepository;
import com.alejandro.app.rest.Services.ExpenseService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @Test
    public void testGetAllExpenses() {
        when(expenseRepository.findAll()).thenReturn(Arrays.asList(new Expense(), new Expense()));

        List<Expense> expenses = expenseService.getAllExpenses();

        Mockito.verify(expenseRepository, Mockito.times(1)).findAll();
        assertNotNull(expenses);
        assertFalse(expenses.isEmpty());
    }

    @Test
    public void testCreateExpense() {
        Expense newExpense = new Expense();
        expenseService.createExpense(newExpense);
        Mockito.verify(expenseRepository, Mockito.times(1)).save(newExpense);
    }

    @Test
    public void testUpdateExpense() {
        long expenseId = 1L;
        Expense existingExpense = new Expense();
        existingExpense.setId(expenseId);
        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(existingExpense));

        Expense updatedExpense = new Expense();
        updatedExpense.setName("Updated Expense");

        Expense result = expenseService.updateExpense(expenseId, updatedExpense);

        Mockito.verify(expenseRepository, Mockito.times(1)).save(existingExpense);

        assertEquals(updatedExpense.getName(), existingExpense.getName());
    }

    @Test
    public void testUpdateExpenseNonexistentExpense() {
        long expenseId = 1L;

        when(expenseRepository.findById(expenseId)).thenReturn(Optional.empty());
        Expense updatedExpense = new Expense();
        assertThrows(RuntimeException.class, () -> expenseService.updateExpense(expenseId, updatedExpense));
        Mockito.verify(expenseRepository, Mockito.times(0)).save(any());
    }
}

