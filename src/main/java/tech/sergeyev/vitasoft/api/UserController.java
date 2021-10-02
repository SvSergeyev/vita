package tech.sergeyev.vitasoft.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.sergeyev.vitasoft.persistence.model.requests.Request;
import tech.sergeyev.vitasoft.persistence.model.users.Person;
import tech.sergeyev.vitasoft.service.PersonService;
import tech.sergeyev.vitasoft.service.RequestService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    /*
Пользователь может
•	создавать заявки
+•	просматривать созданные им заявки
•	редактировать созданные им заявки в статусе «черновик»
•	отправлять заявки на рассмотрение оператору.
Пользователь НЕ может:
•	редактировать отправленные на рассмотрение заявки
•	видеть заявки других пользователей
•	принимать заявки
•	отклонять заявки
•	назначать права
•	смотреть список пользователей
 */

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
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PutMapping("/{id}/set-operator")
//    public ResponseEntity<?> changeRole(@PathVariable(name = "id") int id,
//                                        HttpServletRequest request) {
//        Person person = personService.getPersonById(id);
//        person.setRoles(Collections.singletonList(new Role("ROLE_OPERATOR")));
//        final boolean updated = personService.update(id, person);
//        return updated
//                ? new ResponseEntity<>(HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//    }
}
