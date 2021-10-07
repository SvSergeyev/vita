package tech.sergeyev.vitasoft.service.temp;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.sergeyev.vitasoft.persistence.model.users.RoleNames;
import tech.sergeyev.vitasoft.persistence.repository.RoleRepository;
import tech.sergeyev.vitasoft.persistence.model.users.Role;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName(RoleNames name) {
        return roleRepository.findByName(name).orElseThrow(() -> new Exception("Role not found"));
    }
}
