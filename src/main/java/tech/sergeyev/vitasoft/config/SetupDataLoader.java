package tech.sergeyev.vitasoft.config;

import lombok.NonNull;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tech.sergeyev.vitasoft.persistence.dao.PersonRepository;
import tech.sergeyev.vitasoft.persistence.dao.RoleRepository;
import tech.sergeyev.vitasoft.persistence.model.users.Person;
import tech.sergeyev.vitasoft.persistence.model.users.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public SetupDataLoader(PersonRepository personRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder encoder) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(@NonNull ContextRefreshedEvent contextRefreshedEvent) {
        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_OPERATOR");
        createRoleIfNotFound("ROLE_USER");
        Person admin = getAccount("Slava", "slava@ya.ru", encoder.encode("111111"), roleRepository.findByName("ROLE_ADMIN"));
        Person user = getAccount("Ivan", "ivan@ya.ru", encoder.encode("222222"), roleRepository.findByName("ROLE_USER"));
        Person operator= getAccount("Oleg", "oleg@ya.ru", encoder.encode("333333"), roleRepository.findByName("ROLE_OPERATOR"));
        List<Person> accounts = new ArrayList<>();
        accounts.add(admin);
        accounts.add(user);
        accounts.add(operator);
        for (Person account : accounts) {
            if (personRepository.findByEmail(account.getEmail()) == null) {
                personRepository.save(account);
            }
        }
    }

    @Transactional
    void createRoleIfNotFound(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
    }

    Person getAccount(String name, String email, String password, Role role) {
        Person account = new Person();
        account.setName(name);
        account.setEmail(email);
        account.setPassword(password);
        account.setRoles(Collections.singleton(role));
        return account;
    }


}
