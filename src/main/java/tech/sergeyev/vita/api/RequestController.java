package tech.sergeyev.vita.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tech.sergeyev.vita.payload.response.ShowUsersRequestsResponse;
import tech.sergeyev.vita.persistence.model.requests.Request;
import tech.sergeyev.vita.persistence.model.requests.Statement;
import tech.sergeyev.vita.persistence.model.users.Person;
import tech.sergeyev.vita.persistence.model.users.Role;
import tech.sergeyev.vita.persistence.model.users.RoleNames;
import tech.sergeyev.vita.service.PersonServiceImpl;
import tech.sergeyev.vita.service.RequestService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/requests")
@CrossOrigin(origins = "http://localhost:4200")
public class RequestController {

    public static final Logger LOGGER = LoggerFactory.getLogger(RequestController.class);

    private final RequestService requestService;
    private final PersonServiceImpl personService;

    public RequestController(RequestService requestService,
                             PersonServiceImpl personService) {
        this.requestService = requestService;
        this.personService = personService;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_OPERATOR')")
    @GetMapping
    public ResponseEntity<?> show() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person user = personService.getPersonByEmail(userDetails.getUsername());

        List<Request> usersRequests;
        List<Request> submittedRequests;
        ShowUsersRequestsResponse response = new ShowUsersRequestsResponse();

        if (user.getRoles().contains(new Role(RoleNames.ROLE_USER))) {
            usersRequests = requestService.getAllByAuthor(user);
            if (usersRequests != null) {
                response.setUsersRequests(usersRequests);
            }
        }

        if (user.getRoles().contains(new Role(RoleNames.ROLE_OPERATOR))) {
            submittedRequests = requestService.getAllByStatus(Statement.SUBMITTED);
            if (submittedRequests != null) {
                response.setSubmittedRequests(submittedRequests);
            }
        }

        if (response.getSubmittedRequests() != null || response.getUsersRequests() != null) {
            return ResponseEntity.ok(response);
        } else if (response.getSubmittedRequests() == null && response.getUsersRequests() == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().build();
    }


    //TODO: создание заявки от пользователя
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/new")
    public ResponseEntity<?> create(@Valid @RequestBody Request userRequest) {

        return null;
    }



    //TODO: показ конкретной заявки по id
    @GetMapping("/{requestId}")
    public ResponseEntity<?> read(@PathVariable(name = "requestId") int id) {
        /*
        так было в таймлифе и сейчас, в общем-то, ничего +/- не изменится
        Request request = requestService.getRequestById(id);
        return request != null
                ? new ResponseEntity<>(request, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);*/
        return null;
    }

    //TODO: обновление заявки
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{requestId}")
    public ResponseEntity<?> update(@PathVariable(name = "requestId") int id,
                                    @RequestBody Request request) {
        /*
        boolean updated = requestService.update(id, request);
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);*/
        return null;
    }


}
