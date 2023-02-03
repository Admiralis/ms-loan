package fr.omg.admiralis.msloan.student;

import fr.omg.admiralis.msloan.course.Course;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student {

    @Id
    private String id;

    @Transient
    private String firstName;
    @Transient
    private String lastName;

    private Course course;
}
