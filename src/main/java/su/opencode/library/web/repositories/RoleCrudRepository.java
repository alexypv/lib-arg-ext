package su.opencode.library.web.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import su.opencode.library.web.model.entities.RoleEntity;

@Repository
public interface RoleCrudRepository extends CrudRepository<RoleEntity, Integer> {

    RoleEntity findRoleEntitiesByName(String name);
}

