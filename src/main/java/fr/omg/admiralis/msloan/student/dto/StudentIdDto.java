package fr.omg.admiralis.msloan.student.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class StudentIdDto {
    @Id
    String id;
}
