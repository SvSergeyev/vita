package persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import persistence.model.users.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

}
