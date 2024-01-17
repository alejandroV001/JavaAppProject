package com.alejandro.app.rest.Controllers;
import com.alejandro.app.rest.Models.Expense;
import com.alejandro.app.rest.Services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/expenses")
public class ExpenceController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/all")
    public ResponseEntity<List<Expense>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    @PutMapping("/update/{expenseId}")
    public ResponseEntity<Expense> updateExpense(@PathVariable long expenseId, @RequestBody Expense updatedExpense) {
        try {
            Expense expense = expenseService.updateExpense(expenseId, updatedExpense);
            return ResponseEntity.ok(expense);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
