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
}
