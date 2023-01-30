package fr.omg.admiralis.msloan.computer;

import fr.omg.admiralis.msloan.computer.comments.Comment;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "computers")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Computer {
    private String id;
    private String serialNumber;
    private String processor;
    private String ram;
    private String condition;
    @DBRef
    private List<Comment> comments;
}
