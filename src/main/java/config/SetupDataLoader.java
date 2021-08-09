package config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import persistence.dao.PersonRepository;
import persistence.dao.RoleRepository;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    PersonRepository personRepository;
    RoleRepository roleRepository;
    PasswordEncoder encoder;

    public SetupDataLoader(PersonRepository personRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder encoder) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

    }
}
