package tech.sergeyev.vita.api.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.sergeyev.vita.persistence.model.users.Person;
import tech.sergeyev.vita.security.services.UserDetailsImpl;
import tech.sergeyev.vita.security.services.UserDetailsServiceImpl;
import tech.sergeyev.vita.service.PersonServiceImpl;

@RestController
@RequestMapping("/profile")
public class PersonalAreaController {
    private final UserDetailsServiceImpl userDetailsService;
    private final PersonServiceImpl personService;

    public PersonalAreaController(UserDetailsServiceImpl userDetailsService,
                                  PersonServiceImpl personService) {
        this.userDetailsService = userDetailsService;
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<?> show() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person user = personService.getPersonByEmail(userDetails.getEmail());
        return ResponseEntity.ok(user);
    }
}
