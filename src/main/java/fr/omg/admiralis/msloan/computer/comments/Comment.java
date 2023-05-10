package fr.omg.admiralis.msloan.computer.comments;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
public class Comment {

    @Id
    private String id;
    private String content;
}
