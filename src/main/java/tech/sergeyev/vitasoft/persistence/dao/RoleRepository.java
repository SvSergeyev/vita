package tech.sergeyev.vitasoft.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.sergeyev.vitasoft.persistence.model.users.Role;
import tech.sergeyev.vitasoft.persistence.model.users.RoleNames;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
//    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Optional<Role> findByName(/*@Param("name")*/ RoleNames name);
}
