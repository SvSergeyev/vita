package tech.sergeyev.vitasoft.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.sergeyev.vitasoft.persistence.repository.RequestRepository;
import tech.sergeyev.vitasoft.persistence.model.requests.Request;
import tech.sergeyev.vitasoft.persistence.model.requests.Statement;
import tech.sergeyev.vitasoft.persistence.model.users.Person;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class RequestService {
    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
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

    public Request getRequestById(int request_id) {
        return requestRepository.findById(request_id);
    }

    public void updateText(int id, String message) {
        getRequestById(id).setMessage(message);
    }

    public void updateStatus(int id, Statement statement) {
        getRequestById(id).setStatement(statement);
    }

    public boolean update(int id, Request request) {
        Request toBeUpdated = requestRepository.findById(id);
        if (toBeUpdated != null) {
            toBeUpdated.setMessage(request.getMessage());
            toBeUpdated.setStatement(request.getStatement());
            return true;
        }
        return false;
    }

    public List<Request> getAllByStatus(Statement statement) {
        return requestRepository.findAllByStatement(statement);
    }

    public List<Request> getAllByAuthor(Person author) {
        return requestRepository.findAllByAuthor(author);
    }
}
