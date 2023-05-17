package fr.omg.admiralis.msloan.loan;

import fr.omg.admiralis.msloan.loan.model.Loan;
import fr.omg.admiralis.msloan.loan.model.LoanStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends MongoRepository<Loan, String> {
    List<Loan> findByComputerId(@Param("computerId")String computerId);

    List<Loan> findByStudentId(@Param("studentId")String studentId);

    List<Loan> findByCourseId(@Param("courseId")String courseId);

    List<Loan> findByCourseIdAndLoanStatus(@Param("courseId")String courseId, @Param("status")LoanStatus status);

    List<Loan> findByComputerIdAndLoanStatus(@Param("computerId")String computerId, @Param("status")LoanStatus status);
}
