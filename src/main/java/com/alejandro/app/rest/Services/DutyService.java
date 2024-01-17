package com.alejandro.app.rest.Services;
import com.alejandro.app.rest.Models.Duty;
import com.alejandro.app.rest.Models.Expense;
import com.alejandro.app.rest.Models.User;
import com.alejandro.app.rest.Repository.DutyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DutyService {

    @Autowired
    private DutyRepository dutyRepository;
    public void createDuty(Duty duty) {
        dutyRepository.save(duty);
    }

    public List<Duty> getAllDebtsForUser(User user) {
        return dutyRepository.findByDebtor(user);
    }
    public List<Duty> getAllCreditsForUser(User user) {
        return dutyRepository.findByCreditor(user);
    }
    public List<Duty> getAllDebtsAndCreditsForUser(User user) {
        List<Duty> debtsAsDebtor = dutyRepository.findByDebtor(user);
        List<Duty> debtsAsCreditor = dutyRepository.findByCreditor(user);
        debtsAsDebtor.addAll(debtsAsCreditor);
        return debtsAsDebtor;
    }
//    public List<Duty> getDebtsForUser(User user) {
//        return dutyRepository.findByDebtorAndStatus(user, true);
//    }
//
//    public List<Duty> getCreditsForUser(User user) {
//        return dutyRepository.findByCreditorAndStatus(user, true);
//    }
//
//    public List<Duty> getPaidDebtsForUser(User user) {
//        return dutyRepository.findByDebtorAndStatus(user, false);
//    }
//
//    public List<Duty> getPaidCreditsForUser(User user) {
//        return dutyRepository.findByCreditorAndStatus(user, false);
//    }
}
