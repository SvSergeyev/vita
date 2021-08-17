package tech.sergeyev.vitasoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.sergeyev.vitasoft.persistence.model.requests.Request;
import tech.sergeyev.vitasoft.persistence.model.requests.Statement;
import tech.sergeyev.vitasoft.persistence.model.users.Person;
import tech.sergeyev.vitasoft.service.PersonService;
import tech.sergeyev.vitasoft.service.RequestService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("*/operator")
public class OperatorController {
    private final RequestService requestService;
    private final PersonService personService;

    public OperatorController(RequestService requestService, PersonService personService) {
        this.requestService = requestService;
        this.personService = personService;
    }

    @GetMapping()
    public String redirect(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_OPERATOR")) {
            Person operator = personService.getPersonByEmail(request.getUserPrincipal().getName());
            return ("redirect:/lk/operator/" + operator.getId());
        }
        else {
            return "redirect:/login?logout";
        }
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        List<Request> submittedRequests = requestService.getAllByStatus(Statement.SUBMITTED);
        model.addAttribute("submittedRequests", submittedRequests);
        return "personal/operator";
    }
}
