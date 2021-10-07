package tech.sergeyev.vita.payload.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
    String token;
    final String type = "Bearer";
    Integer id;
    String username;
    List<String> roles;
}
