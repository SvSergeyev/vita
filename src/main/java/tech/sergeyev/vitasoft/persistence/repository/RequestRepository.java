package tech.sergeyev.vitasoft.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.sergeyev.vitasoft.persistence.model.requests.Request;
import tech.sergeyev.vitasoft.persistence.model.requests.Statement;
import tech.sergeyev.vitasoft.persistence.model.users.Person;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    @Query("SELECT r FROM Request r WHERE r.author = :author")
    List<Request> findAllByAuthor(@Param("author") Person author);

    @Query("SELECT r FROM Request r WHERE r.id = :id")
    Request findById(@Param("id") int id);

    @Query("SELECT r FROM Request r WHERE r.statement = :statement")
    List<Request> findAllByStatement(@Param("statement") Statement statement);
}
