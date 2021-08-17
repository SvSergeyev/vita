package tech.sergeyev.vitasoft.persistence.model.users;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import tech.sergeyev.vitasoft.persistence.model.requests.Request;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @NotEmpty(message = "Поле не может быть пустым")
    @Setter
    String name;

    @Email
    @Setter
    String email;

    @Setter
    String password;

    @ManyToMany
    @JoinTable(
            name = "people_roles",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @Setter
    Collection<Role> roles;

    @OneToMany(mappedBy = "author")
    List<Request> requests;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}
