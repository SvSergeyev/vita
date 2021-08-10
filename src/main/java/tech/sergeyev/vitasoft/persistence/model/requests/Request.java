package tech.sergeyev.vitasoft.persistence.model.requests;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import tech.sergeyev.vitasoft.persistence.model.users.Person;

import javax.persistence.*;

@Entity
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    Statement statement;
    String message;

    @ManyToOne()
    Person author;
}
