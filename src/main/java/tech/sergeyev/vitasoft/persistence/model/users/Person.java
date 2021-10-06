package tech.sergeyev.vitasoft.persistence.model.users;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import tech.sergeyev.vitasoft.persistence.model.requests.Request;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @NotBlank(message = "Поле не может быть пустым")
    @Setter
    @Size(min = 2, max = 20)
    String name;

    @Email
    @Setter
    @NotBlank
    @Size(max = 50)
    String email;

    @Setter
    @NotBlank
    @Size(max = 120)
    String password;


    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "people_roles",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @Setter
    Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "author")
    List<Request> requests;

    public Person(@NotBlank(message = "Поле не может быть пустым") @Size(min = 2, max = 20) String name,
                  @Email @NotBlank @Size(max = 50) String email,
                  @NotBlank @Size(max = 120) String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

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
