package fr.omg.admiralis.msloan.loan.model;

import fr.omg.admiralis.msloan.computer.Computer;
import fr.omg.admiralis.msloan.course.Course;
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

    LoanStatus loanStatus;
    Course course;
    @NonNull
    Computer computer;
}
