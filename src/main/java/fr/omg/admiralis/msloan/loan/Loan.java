package fr.omg.admiralis.msloan.loan;

import fr.omg.admiralis.msloan.computer.Computer;
import fr.omg.admiralis.msloan.course.Course;
import fr.omg.admiralis.msloan.loan.dto.DepositState;
import fr.omg.admiralis.msloan.loan.dto.LoanType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "loans")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    String id;

    @NonNull
    LocalDate startDate;
    LocalDate endDate;
    DepositState depositState;
    LoanType loanType;
    Course course;

    @NonNull
    Computer computer;
}
