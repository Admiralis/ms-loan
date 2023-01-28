package fr.omg.admiralis.msloan.loan;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    public Loan findById(String id) {
        return loanRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Loan save(Loan newLoan) {
        return null;
    }

    public Loan update(String id, Loan newLoan) {
        return null;
    }

    public void deleteById(String id) {

    }
}
