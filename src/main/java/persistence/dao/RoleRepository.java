package persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import persistence.model.users.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
