package fr.omg.admiralis.msloan.loan;

import fr.omg.admiralis.msloan.loan.model.Loan;
import fr.omg.admiralis.msloan.loan.model.LoanStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LoanRepository extends MongoRepository<Loan, String> {
    List<Loan> findByComputerId(String computerId);

    List<Loan> findByStudentId(String studentId);

    List<Loan> findByCourseId(String courseId);

    List<Loan> findByCourseIdAndLoanStatus(String courseId, LoanStatus status);

    List<Loan> findByComputerIdAndLoanStatus(String computerId, LoanStatus status);
}
