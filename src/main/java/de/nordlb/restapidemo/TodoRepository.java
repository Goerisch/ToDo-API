package de.nordlb.restapidemo;

import java.util.Set;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, Integer> {
  Set<Todo> findAllByUserId(UUID userId);

}
