package fr.omg.admiralis.msloan.loan;

import fr.omg.admiralis.msloan.loan.model.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoanRepository extends MongoRepository<Loan, String> {
}
