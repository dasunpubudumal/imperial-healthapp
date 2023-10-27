package org.health.imperialhealthapp.repositories;

import org.health.imperialhealthapp.models.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findUserByUsername(String username);

}
