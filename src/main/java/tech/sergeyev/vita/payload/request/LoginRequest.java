package tech.sergeyev.vita.payload.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class LoginRequest {
    @NotBlank
    String username;
    @NotBlank
    String password;
}
