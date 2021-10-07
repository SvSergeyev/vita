package tech.sergeyev.vitasoft.controller.deprecated;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.sergeyev.vitasoft.persistence.model.requests.Request;
import tech.sergeyev.vitasoft.persistence.model.requests.Statement;
import tech.sergeyev.vitasoft.persistence.model.users.Person;
import tech.sergeyev.vitasoft.service.RequestService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/operator")
public class OperatorController {
    private final RequestService requestService;
    private final PersonService personServiceImpl;

    public OperatorController(RequestService requestService,
                              PersonService personServiceImpl) {
        this.requestService = requestService;
        this.personServiceImpl = personServiceImpl;
    }

    @GetMapping()
    public String redirectToPersonalPage() {
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, HttpServletRequest httpRequest) {
        Person requestingUser = personServiceImpl.getPersonByEmail(httpRequest.getUserPrincipal().getName());
        if (id != requestingUser.getId() || !httpRequest.isUserInRole("ROLE_OPERATOR")) {
            return "redirect:/";
        }
        List<Request> submittedRequests = requestService.getAllByStatus(Statement.SUBMITTED);
        for (Request request : submittedRequests) {
            String message = request.getMessage();
            StringBuilder sb = new StringBuilder();
            char[] chars = message.toCharArray();
            for (char aChar : chars) {
                sb.append(aChar);
                sb.append("-");
                requestService.updateText(request.getId(), sb.toString());
            }
        }
        model.addAttribute("submittedRequests", submittedRequests);
        return "personal/operator";
    }
}
