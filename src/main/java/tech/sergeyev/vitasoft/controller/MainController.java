package tech.sergeyev.vitasoft.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.sergeyev.vitasoft.persistence.model.users.Person;
import tech.sergeyev.vitasoft.service.PersonService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class MainController {
    private final PersonService personService;
    private final static Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    public MainController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public String index(HttpServletRequest request) {
        Person person = personService.getPersonByEmail(request.getUserPrincipal().getName());
        LOGGER.info("\n\n\nPERSON: " + person + "\n\n");
        if (request.isUserInRole("ADMIN")) {
            LOGGER.info("ROLE=ADMIN");
            return "redirect:/admin/" + person.getId();
        } else if (request.isUserInRole("OPERATOR")) {
            LOGGER.info("ROLE=OPERATOR");
            return "redirect:/operator/" + person.getId();
        } else if (request.isUserInRole("USER")) {
            LOGGER.info("ROLE=USER");
            return "redirect:/user/" + person.getId();
        }
        return "redirect:/login?logout";
//        return ("redirect:/lk");
    }
}
