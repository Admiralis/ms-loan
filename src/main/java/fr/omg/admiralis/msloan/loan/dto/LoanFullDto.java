package fr.omg.admiralis.msloan.loan.dto;

import fr.omg.admiralis.msloan.loan.model.DepositState;
import fr.omg.admiralis.msloan.loan.model.LoanType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanFullDto {
    LocalDate start;
    LocalDate end;
    DepositState depositState;
    LoanType loanType;
}
