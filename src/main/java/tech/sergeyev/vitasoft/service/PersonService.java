package tech.sergeyev.vitasoft.service;

import tech.sergeyev.vitasoft.persistence.model.users.Person;

import java.util.List;

public interface PersonService {

    boolean update(int id, Person person);
    boolean delete(int id);
    List<Person> getAll();
    List<Person> getAllPeopleByRole(String name);
    Person getPersonByEmail(String email);
    Person getPersonById(int id);

}
