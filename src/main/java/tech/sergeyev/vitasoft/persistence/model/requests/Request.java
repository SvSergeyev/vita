package tech.sergeyev.vitasoft.persistence.model.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
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

    @CreatedDate
    LocalDateTime timeOfCreate;

    @JsonIgnore
    @ManyToOne(cascade=CascadeType.ALL)
    Person author;

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", statement=" + statement +
                ", message='" + message + '\'' +
                ", timeOfCreate=" + timeOfCreate +
                ", author=" + author +
                '}';
    }
}
