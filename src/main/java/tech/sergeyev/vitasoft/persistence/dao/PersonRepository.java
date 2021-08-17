package tech.sergeyev.vitasoft.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.sergeyev.vitasoft.persistence.model.users.Person;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    @Query("SELECT p FROM Person p WHERE p.email = :email")
    Person findByEmail(@Param("email") String email);

    @Query("SELECT p FROM Person p WHERE p.id = :id")
    Person findById(@Param("id") int id);

    @Query("SELECT p FROM Person p INNER JOIN p.roles r WHERE r.name = :name")
    List<Person> findAllByRoles(@Param("name") String name);
}