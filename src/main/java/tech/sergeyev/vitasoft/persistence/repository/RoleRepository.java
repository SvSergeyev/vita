package tech.sergeyev.vitasoft.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.sergeyev.vitasoft.persistence.model.users.Role;
import tech.sergeyev.vitasoft.persistence.model.users.RoleNames;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleNames name);
}
