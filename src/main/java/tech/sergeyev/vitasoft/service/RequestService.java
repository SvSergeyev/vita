package tech.sergeyev.vitasoft.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.sergeyev.vitasoft.persistence.dao.RequestRepository;
import tech.sergeyev.vitasoft.persistence.model.requests.Request;
import tech.sergeyev.vitasoft.persistence.model.requests.Statement;
import tech.sergeyev.vitasoft.persistence.model.users.Person;

import java.time.LocalDateTime;

@Service
@Transactional
public class RequestService {
    private final PersonService personService;
    private final RequestRepository requestRepository;
    private final static Logger LOGGER = LoggerFactory.getLogger(RequestService.class);


    public RequestService(PersonService personService,
                          RequestRepository requestRepository) {
        this.personService = personService;
        this.requestRepository = requestRepository;
    }

    public void create(Person author, String message) {
//        Person author = personService.getPersonById(id);
        LOGGER.info("\ncreate()\n");
        LOGGER.info("AUTHOR:" + author);
        Request request = new Request();
        request.setMessage(message);
        request.setStatement(Statement.DRAFT);
        request.setAuthor(author);
        request.setTimeOfCreate(LocalDateTime.now().withSecond(0));
        requestRepository.save(request);
    }
}
