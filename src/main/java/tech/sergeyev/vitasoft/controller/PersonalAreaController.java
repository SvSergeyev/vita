package tech.sergeyev.vitasoft.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.sergeyev.vitasoft.service.PersonService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/lk")
public class PersonalAreaController {
    private final PersonService personService;

    public PersonalAreaController(PersonService personService) {
        this.personService = personService;
    }
    private final static Logger LOGGER = LoggerFactory.getLogger(PersonalAreaController.class);

    @GetMapping()
    public String identifyRoleAndRedirect(HttpServletRequest request) {
//        Person person = personService.getPersonByEmail(request.getUserPrincipal().getName());
//        int id = person.getId();
//        if (request.isUserInRole("ADMIN")) {
//            return "redirect:/lk/admin/" + id;
//        } else if (request.isUserInRole("OPERATOR")) {
//            return "redirect:/lk/operator/" + id;
//        } else if (request.isUserInRole("USER")) {
//            return "redirect:/lk/user/" + id;
//        }
        if (request.isUserInRole("ADMIN")) {
            return "redirect:/lk/admin";
        } else if (request.isUserInRole("OPERATOR")) {
            return "redirect:/lk/operator";
        } else if (request.isUserInRole("USER")) {
            return "redirect:/lk/user";
        }
        return ("redirect:/login?logout");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String showAdminAccount(Model model, HttpServletRequest request) {
        model.addAttribute("person", personService.getPersonByEmail(request.getUserPrincipal().getName()));
        model.addAttribute("people", personService.getAllPeople());
        return "personal/admin";
    }

    @PreAuthorize("hasRole('ROLE_OPERATOR')")
    @GetMapping("/operator")
    public String showOperatorAccount(Model model, HttpServletRequest request) {
        model.addAttribute("person", personService.getPersonByEmail(request.getUserPrincipal().getName()));
        return "personal/operator";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("user")
    public String showUserAccount(Model model, HttpServletRequest request) {
        model.addAttribute("person", personService.getPersonByEmail(request.getUserPrincipal().getName()));
        return "personal/user";
    }

    @GetMapping("**/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.getPersonById(id));
        return "personal/lk";
    }
}
