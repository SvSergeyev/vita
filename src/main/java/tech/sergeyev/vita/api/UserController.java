package tech.sergeyev.vita.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tech.sergeyev.vita.persistence.model.requests.Request;
import tech.sergeyev.vita.persistence.model.users.Person;
import tech.sergeyev.vita.service.PersonService;
import tech.sergeyev.vita.service.RequestService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public class UserController {

    private final RequestService requestService;
    private final PersonService personService;

    public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    public UserController(RequestService requestService,
                          PersonService personService) {
        this.requestService = requestService;
        this.personService = personService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<List<Request>> show(@PathVariable(name = "id") int id) {
        String requestingUserUsername = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Person requestedUser = personService.getPersonById(id);
        String requestedUserUsername = requestedUser.getEmail();
        if (!requestingUserUsername.equals(requestedUserUsername)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<Request> requests = requestService.getAllByAuthor(requestedUser);
        return requests != null
                ? new ResponseEntity<>(requests, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
