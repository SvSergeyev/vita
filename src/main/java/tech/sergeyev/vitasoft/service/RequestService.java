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
import java.util.List;

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
        Request request = new Request();
        request.setMessage(message);
        request.setStatement(Statement.DRAFT);
        request.setAuthor(author);
        request.setTimeOfCreate(LocalDateTime.now().withSecond(0));
        requestRepository.save(request);
    }

    public Request getById(int request_id) {
        return requestRepository.findById(request_id);
    }

    public void updateText(int id, String message) {
        getById(id).setMessage(message);
    }

    public void updateStatus(int id, Statement statement) {
        getById(id).setStatement(statement);
    }

    public List<Request> getAllByStatus(Statement statement) {
        return requestRepository.findAllByStatement(statement);
    }
}
