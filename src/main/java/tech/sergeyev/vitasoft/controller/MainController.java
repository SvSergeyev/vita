package tech.sergeyev.vitasoft.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.sergeyev.vitasoft.persistence.dao.PersonRepository;
import tech.sergeyev.vitasoft.persistence.model.users.Person;
import tech.sergeyev.vitasoft.service.PersonService;

@Controller
@RequestMapping("/")
public class MainController {
    public static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    PersonService personService;
//    PersonRepository personRepository;


//    public MainController(PersonRepository personRepository) {
//        this.personRepository = personRepository;
//    }
//
    public MainController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public String index(Model model) {
        LOGGER.info("В методе get/index");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person user = personService.getPersonByEmail(personService.loadUserByUsername(auth.getName()).getUsername());
        LOGGER.info("\n\n\nВот че получил: " + user);
        model.addAttribute("person", user);
        return ("index");
    }
}