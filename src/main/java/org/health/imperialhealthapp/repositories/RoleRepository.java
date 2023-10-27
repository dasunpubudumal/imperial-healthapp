package org.health.imperialhealthapp.repositories;

import org.health.imperialhealthapp.models.domain.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

    Optional<Role> findByRoleName(String roleName);

}
