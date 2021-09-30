package tech.sergeyev.vitasoft.api.deprecated;

import org.springframework.beans.factory.annotation.Autowired;
import tech.sergeyev.vitasoft.persistence.model.users.Person;
import tech.sergeyev.vitasoft.persistence.model.users.Role;

//@Component
public abstract class RoleValidator {
    protected final PersonService personService;

    @Autowired
    public RoleValidator(PersonService personService) {
        this.personService = personService;
    }

    protected boolean validateRole(String username, String roleName) {
        Person person = personService.getPersonByEmail(username);
//        System.out.println("\n\n\n" + person.getRoles().contains(new Role(role)));
//        System.out.println(person.getRoles());
//        System.out.println(new Role(role));
        return person.getRoles().contains(new Role(roleName));
    }
}
