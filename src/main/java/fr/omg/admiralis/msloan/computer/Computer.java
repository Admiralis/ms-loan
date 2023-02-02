package fr.omg.admiralis.msloan.computer;

import fr.omg.admiralis.msloan.computer.comments.Comment;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "computers")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Computer {
    @Id
    private String id;
    @Transient
    private String serialNumber;
    @Transient
    private String processor;
    @Transient
    private String ram;
    @Transient
    private String condition;

    @Transient
    private ComputerStatus computerStatus;
    @DBRef
    @Transient
    private List<Comment> comments;
}
