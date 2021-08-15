package tech.sergeyev.vitasoft.persistence.model.requests;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import tech.sergeyev.vitasoft.persistence.model.users.Person;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    Statement statement;
    String message;
    LocalDateTime timeOfCreate;
    @ManyToOne(cascade=CascadeType.ALL)
    Person author;
}
