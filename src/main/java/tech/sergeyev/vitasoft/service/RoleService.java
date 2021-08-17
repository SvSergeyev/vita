package tech.sergeyev.vitasoft.service;

import org.springframework.stereotype.Service;
import tech.sergeyev.vitasoft.persistence.dao.RoleRepository;
import tech.sergeyev.vitasoft.persistence.model.users.Role;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }
}
