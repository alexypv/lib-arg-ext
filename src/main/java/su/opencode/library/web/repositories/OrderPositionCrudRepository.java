package su.opencode.library.web.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import su.opencode.library.web.model.entities.OrderPositionEntity;

@Repository
public interface OrderPositionCrudRepository extends CrudRepository<OrderPositionEntity, Integer> {
}
