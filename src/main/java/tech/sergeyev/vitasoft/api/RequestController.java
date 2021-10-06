package tech.sergeyev.vitasoft.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;
import tech.sergeyev.vitasoft.persistence.model.requests.Request;
import tech.sergeyev.vitasoft.service.RequestService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/requests")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class RequestController {
    private RequestService requestService;
    public static final Logger LOGGER = LoggerFactory.getLogger(RequestController.class);



    //TODO: создание заявки от пользователя
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Request userRequest,
                                    HttpServletRequest httpServletRequest) {
        return null;
    }

    //TODO: показ всех заявок пользователю или оператору
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_OPERATOR')")
    @GetMapping
    public ResponseEntity<List<Request>> show(HttpServletRequest httpServletRequest) {
        /*LOGGER.info("time: " + LocalDateTime.now());
        Person author = personService.getPersonByEmail(
                httpServletRequest.getUserPrincipal().getName()
        );
        List<Request> personalRequests = requestService.getAllByAuthor(author);
        if (!validateRole(author.getEmail(), "ROLE_OPERATOR")) {
            return personalRequests != null
                    ? new ResponseEntity<>(personalRequests, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Request> unacceptedRequests = requestService.getAllByStatus(Statement.DRAFT);
        List<Request> unacceptedAndPersonalRequests = new ArrayList<>(personalRequests);
        for (Request request : unacceptedAndPersonalRequests) {
            for (Request unacceptedRequest : unacceptedRequests) {
                if (unacceptedRequest.getAuthor() != request.getAuthor()) {
                    unacceptedAndPersonalRequests.add(unacceptedRequest);
                }
            }
        }
        return new ResponseEntity<>(unacceptedAndPersonalRequests, HttpStatus.OK);*/
        return null;
    }

    //TODO: показ конкретной заявки по id
    @GetMapping("/{requestId}")
    public ResponseEntity<?> read(@PathVariable(name = "requestId") int id) {
        /*Request request = requestService.getRequestById(id);
        return request != null
                ? new ResponseEntity<>(request, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);*/
        return null;
    }

    //TODO: обновление заявки
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{requestId}")
    public ResponseEntity<?> update(@PathVariable(name = "requestId") int id,
                                    @RequestBody Request request) {
        /*boolean updated = requestService.update(id, request);
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);*/
        return null;
    }


}
