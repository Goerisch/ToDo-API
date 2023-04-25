package de.nordlb.restapidemo;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UUID> {
  Optional<User> findByEmailAndPassword(String email, String password);
  Optional<User> findBySecret(String secret);
}
