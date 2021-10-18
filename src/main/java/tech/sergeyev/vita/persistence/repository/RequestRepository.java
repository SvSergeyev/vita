package tech.sergeyev.vita.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.sergeyev.vita.persistence.model.requests.Request;
import tech.sergeyev.vita.persistence.model.requests.Statement;
import tech.sergeyev.vita.persistence.model.users.Person;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    List<Request> findAllByAuthor(@Param("author") Person author);

    Request findById(@Param("id") int id);

    List<Request> findAllByStatement(@Param("statement") Statement statement);
}
