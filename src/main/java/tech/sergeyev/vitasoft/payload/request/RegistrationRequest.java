package tech.sergeyev.vitasoft.payload.request;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import tech.sergeyev.vitasoft.persistence.model.users.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
public class RegistrationRequest {

    @NotBlank
    @Size(min = 2, max = 30)
    String name;

    @NotBlank
    @Email
    @Size(max = 30)
    String email;

    @NotBlank
    @Size(min = 6, max = 20)
    String password;

    Set<String> roles;
}
