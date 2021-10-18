package tech.sergeyev.vita.api.auth;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tech.sergeyev.vita.payload.request.LoginRequest;
import tech.sergeyev.vita.payload.request.RegistrationRequest;
import tech.sergeyev.vita.payload.response.JwtResponse;
import tech.sergeyev.vita.persistence.model.users.Person;
import tech.sergeyev.vita.persistence.model.users.Role;
import tech.sergeyev.vita.persistence.model.users.RoleNames;
import tech.sergeyev.vita.persistence.repository.PersonRepository;
import tech.sergeyev.vita.persistence.repository.RoleRepository;
import tech.sergeyev.vita.security.jwt.JwtUtils;
import tech.sergeyev.vita.security.services.UserDetailsImpl;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));
        LOGGER.info("AUTHENTICATION: " + authentication.getPrincipal() + ", " + authentication.getCredentials());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        JwtResponse response = new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail(), roles);
        JwtResponse responseWithUsername = new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles);
        LOGGER.info("RESPONSE FROM ANSWER (email): " + response);
        LOGGER.info("RESPONSE FROM ANSWER (username): " + responseWithUsername);
        return ResponseEntity.ok(
                new JwtResponse(jwt,
                        userDetails.getId(),
                        userDetails.getUsername(),
                        roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registration(@Valid @RequestBody RegistrationRequest request) {
        if (personRepository.existsByEmail(request.getEmail())) {
            return new ResponseEntity<>(
                    "Error: Email is already in use",
                    HttpStatus.BAD_REQUEST
            );
        }
        Person user = new Person(
                request.getName(),
                request.getEmail(),
                encoder.encode(request.getPassword())
        );
        Set<String> rolesFromRequest = request.getRoles();
        if (rolesFromRequest != null &&
                (rolesFromRequest.contains("ROLE_OPERATOR") || rolesFromRequest.contains("ROLE_ADMIN"))) {
            return new ResponseEntity<>(
                    "Error: Invalid role",
                    HttpStatus.BAD_REQUEST);
        } else {
            Role role = roleRepository.findByName(RoleNames.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Role USER not found"));
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
        }
        personRepository.save(user);
        return ResponseEntity.ok("Registration completed successfully! Your login: " + user.getEmail());
    }
}
