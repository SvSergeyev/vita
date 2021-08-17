package tech.sergeyev.vitasoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.sergeyev.vitasoft.persistence.model.users.Person;
import tech.sergeyev.vitasoft.service.PersonService;
import tech.sergeyev.vitasoft.service.RoleService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PersonService personService;
    private final RoleService roleService;

    public AdminController(PersonService personService,
                           RoleService roleService) {
        this.personService = personService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String redirectToPersonalPage() {
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, HttpServletRequest httpRequest) {
        Person requestingUser = personService.getPersonByEmail(httpRequest.getUserPrincipal().getName());
        if (id != requestingUser.getId() || !httpRequest.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/";
        }
        model.addAttribute("admin", requestingUser);
        model.addAttribute("users", personService.getAllPeopleByRole("ROLE_USER"));
        model.addAttribute("operators", personService.getAllPeopleByRole("ROLE_OPERATOR"));
        return "personal/admin";
    }

    @PatchMapping("/{id}/edit_roles/{u_id}")
    public String changeRole(@PathVariable("u_id") int u_id, @PathVariable("id") int id) {
        Person modifyingUser = new Person();
        modifyingUser.setRoles(Collections.singleton(roleService.getRoleByName("ROLE_OPERATOR")));
        personService.update(u_id, modifyingUser);
        return "redirect:/";
    }
}
