package tech.sergeyev.vita.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.sergeyev.vita.payload.response.UsersAndOperatorsListResponse;
import tech.sergeyev.vita.persistence.model.users.Person;
import tech.sergeyev.vita.service.PersonServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
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
        UsersAndOperatorsListResponse response = new UsersAndOperatorsListResponse(users, operators);
//        LOGGER.info("Response: " + response);
//        List<List<Person>> responseList = new ArrayList<>();
//        responseList.add(users);
//        responseList.add(operators);
//        List<List<String>> str = new ArrayList<>();
//        List<String> sub1 = new ArrayList<>();
//        List<String> sub2 = new ArrayList<>();
//        sub1.add("a");
//        sub1.add("b");
//        sub2.add("c");
//        sub2.add("d");
//        str.add(sub1);
//        str.add(sub2);
        LOGGER.info("Response: " + response);
        return ResponseEntity.ok(response);
    }
}
