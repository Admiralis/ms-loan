package fr.omg.admiralis.msloan.loan;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {
    public List<Loan> findAll() {
        return new ArrayList<>();
    }

    public Loan findById(String id) {
        return null;
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
