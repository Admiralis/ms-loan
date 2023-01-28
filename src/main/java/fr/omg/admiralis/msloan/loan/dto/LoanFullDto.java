package fr.omg.admiralis.msloan.loan.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanFullDto {
    LocalDate start;
    LocalDate end;
    DepositState depositState;
    LoanType loanType;
}
