package tech.sergeyev.vita.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.sergeyev.vita.persistence.model.users.Person;
import tech.sergeyev.vita.persistence.model.users.RoleNames;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByEmail(String email);

    Person findById(int id);

    @Query("SELECT p FROM Person p INNER JOIN p.roles r WHERE r.name = :name")
    List<Person> findAllByRoles(@Param("name") RoleNames name);

    Boolean existsByEmail(String email);
}
