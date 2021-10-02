package tech.sergeyev.vitasoft.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.sergeyev.vitasoft.payload.response.UsersAndOperatorsListResponse;
import tech.sergeyev.vitasoft.persistence.model.users.Person;
import tech.sergeyev.vitasoft.service.PersonServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/admins")
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    // что делает админ:
    // + просматривает список всех пользователей
    // + возвращает в ответе два списка:
    //   операторы;
    //   пользователи.
    // * может назначать пользователя оператором,
    //   а оператора - пользователем

    private final PersonServiceImpl personService;

    public AdminController(PersonServiceImpl personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<?> show() {
        List<Person> users = personService.getAllPeopleByRole("ROLE_USER");
        List<Person> operators = personService.getAllPeopleByRole("ROLE_OPERATOR");
        return ResponseEntity.ok(
                new UsersAndOperatorsListResponse(users, operators));
    }
}
