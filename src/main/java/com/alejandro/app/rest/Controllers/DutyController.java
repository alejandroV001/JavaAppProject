package com.alejandro.app.rest.Controllers;

import com.alejandro.app.rest.Models.Duty;
import com.alejandro.app.rest.Models.User;
import com.alejandro.app.rest.Repository.UserRepository;
import com.alejandro.app.rest.Services.DutyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/duties")
public class DutyController {

    @Autowired
    private DutyService dutyService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/debts/{userId}")
    public ResponseEntity<List<Duty>> getDebtsForUser(@PathVariable long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent())
        {
            User user = optionalUser.get();
            return ResponseEntity.ok(dutyService.getAllDebtsForUser(user));

        }
        return ResponseEntity.ok(null);
    }

    @GetMapping("/credits/{userId}")
    public ResponseEntity<List<Duty>> getCreditsForUser(@PathVariable long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent())
        {
            User user = optionalUser.get();
            return ResponseEntity.ok(dutyService.getAllCreditsForUser(user));

        }
        return ResponseEntity.ok(null);
    }
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Duty>> getCreditsAndDebtsForUser(@PathVariable long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent())
        {
            User user = optionalUser.get();
            return ResponseEntity.ok(dutyService.getAllDebtsAndCreditsForUser(user));

        }
        return ResponseEntity.ok(null);
    }
}
