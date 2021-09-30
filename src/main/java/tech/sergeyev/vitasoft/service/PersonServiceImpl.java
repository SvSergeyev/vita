package tech.sergeyev.vitasoft.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.sergeyev.vitasoft.persistence.dao.PersonRepository;
import tech.sergeyev.vitasoft.persistence.model.users.Person;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Override
    public boolean update(int id, Person person) {
        Person toBeUpdatedPerson = personRepository.findById(id);
        if (toBeUpdatedPerson != null) {
            toBeUpdatedPerson.setName(person.getName());
            toBeUpdatedPerson.setEmail(person.getEmail());
            toBeUpdatedPerson.setRoles(person.getRoles());
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        Person personBeforeDelete = personRepository.findById(id);
        if (personBeforeDelete != null) {
            personRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    @Override
    public List<Person> getAllPeopleByRole(String name) {
        return personRepository.findAllByRoles(name);
    }

    @Override
    public Person getPersonByEmail(String email) {
        return personRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not fount"));
    }

    @Override
    public Person getPersonById(int id) {
        return personRepository.findById(id);
    }
}
