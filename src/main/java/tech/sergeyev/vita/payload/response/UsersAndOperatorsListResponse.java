package tech.sergeyev.vita.payload.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import tech.sergeyev.vita.persistence.model.users.Person;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsersAndOperatorsListResponse {
    List<Person> users;
    List<Person> operators;
}
