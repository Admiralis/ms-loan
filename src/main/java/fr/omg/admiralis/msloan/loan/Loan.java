package fr.omg.admiralis.msloan.loan;

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
    LocalDate start;
    LocalDate end;
    DepositState depositState;
    LoanType loanType;
}
