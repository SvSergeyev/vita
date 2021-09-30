package tech.sergeyev.vitasoft.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tech.sergeyev.vitasoft.api.deprecated.RoleValidator;
import tech.sergeyev.vitasoft.persistence.model.requests.Request;
import tech.sergeyev.vitasoft.persistence.model.users.Person;
import tech.sergeyev.vitasoft.persistence.model.users.Role;
import tech.sergeyev.vitasoft.service.RequestService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/*
Пользователь может
•	создавать заявки
•	просматривать созданные им заявки
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

@RestController
@RequestMapping("/users")
public class UserRestController extends RoleValidator {
    private final RequestService requestService;
    public static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);

    public UserRestController(RequestService requestService,
                              PersonService personService) {
        super(personService);
        this.requestService = requestService;
    }

    @GetMapping()
    public String redirectToPersonalPage(HttpServletRequest request) {
        if (!request.isUserInRole("ROLE_USER")) {
            LOGGER.info("Not a USER, redirect...");
            return "redirect:/";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person requestingUser = personService.getPersonByEmail(auth.getName());
        LOGGER.info("Redirect to personal page...");
        return "redirect:/user/" + requestingUser.getId();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Request>> show(@PathVariable(name = "id") int id,
                                              HttpServletRequest httpRequest) {
        Person personFromRequest = personService.getPersonByEmail(httpRequest.getUserPrincipal().getName());
        if (personFromRequest.getId() != id) {
            LOGGER.warn("Unauthorized access");
            redirectToPersonalPage(httpRequest);
        }
        Person user = personService.getPersonById(id);
        List<Request> requests = requestService.getAllByAuthor(user);
        return requests != null
                ? new ResponseEntity<>(requests, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}/set-operator")
    public ResponseEntity<?> changeRole(@PathVariable(name = "id") int id,
                                        HttpServletRequest request) {
        if (!validateRole(request.getUserPrincipal().getName(), "ROLE_ADMIN")) {
            LOGGER.warn("There is not enough authority to fulfill the request");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Person person = personService.getPersonById(id);
        person.setRoles(Collections.singletonList(new Role("ROLE_OPERATOR")));
        final boolean updated = personService.update(id, person);
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
