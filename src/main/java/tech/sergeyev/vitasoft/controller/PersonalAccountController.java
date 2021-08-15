package tech.sergeyev.vitasoft.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.sergeyev.vitasoft.persistence.dao.RoleRepository;
import tech.sergeyev.vitasoft.persistence.model.users.Person;
import tech.sergeyev.vitasoft.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Controller
@RequestMapping("/lk")
public class PersonalAccountController {
    private final PersonService personService;
    private final RoleRepository roleRepository;

    public PersonalAccountController(PersonService personService,
                                     RoleRepository roleRepository) {
        this.personService = personService;
        this.roleRepository = roleRepository;
    }
    private final static Logger LOGGER = LoggerFactory.getLogger(PersonalAccountController.class);

    @GetMapping()
    public String identifyAndRedirect(HttpServletRequest request) {
        Person person = personService.getPersonByEmail(request.getUserPrincipal().getName());
        if (request.isUserInRole("ADMIN")) {
            return "redirect:lk/admin/" + person.getId();
        } else if (request.isUserInRole("OPERATOR")) {
            return "redirect:lk/operator/" + person.getId();
        } else if (request.isUserInRole("USER")) {
            return "redirect:lk/user/" + person.getId();
        }
        return "redirect:/login?logout";
    }

    @GetMapping("**/{id}")
    public String show(@PathVariable("id") int id, Model model, HttpServletRequest request) {
        model.addAttribute("person", personService.getPersonById(id));
        if (request.isUserInRole("ROLE_ADMIN")) {
            model.addAttribute("people", personService.getAllPeopleByRole("ROLE_USER"));
            return "personal/admin";
        }
        else if (request.isUserInRole("ROLE_OPERATOR")) {
            return "personal/operator";
        }
        else if (request.isUserInRole("ROLE_USER")) {
            return "personal/user";
        }
        else {
            return "redirect:/login?logout";
        }
    }

    @PatchMapping("**/admin/editUserRoles/{id}")
    public String changeRole(@PathVariable("id") int id, @ModelAttribute Person person) {
        person.setRoles(Collections.singleton(roleRepository.findByName("ROLE_OPERATOR")));
        personService.update(id, person);
        LOGGER.info("Person now: " + personService.getPersonById(id));
        return "redirect:/lk";
    }
}
