package su.opencode.library.web.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import su.opencode.library.web.model.entities.LibraryEntity;
import su.opencode.library.web.model.entities.RoleEntity;
import su.opencode.library.web.model.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface UserCrudRepository extends CrudRepository<UserEntity, Integer> {

    UserEntity findUserEntityByUsername(String username);

    Page<UserEntity> findUserEntityByRolesAndLibraryEntity(RoleEntity roleEntity, LibraryEntity libraryEntity,  Pageable pageable);
}
