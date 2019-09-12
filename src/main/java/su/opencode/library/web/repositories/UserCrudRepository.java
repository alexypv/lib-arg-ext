package su.opencode.library.web.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import su.opencode.library.web.model.entities.LibraryEntity;
import su.opencode.library.web.model.entities.RoleEntity;
import su.opencode.library.web.model.entities.UserEntity;

@Repository
public interface UserCrudRepository extends CrudRepository<UserEntity, Integer> {

    UserEntity findUserEntityByUsername(String username);

    Page<UserEntity> findUserEntityByRolesAndLibraryEntity(RoleEntity roleEntity, LibraryEntity libraryEntity, Pageable pageable);

    @Query("select u from UserEntity u " +
            "join u.roles ur where " +
            "u.libraryEntity.id  = :library_id and " +
            "ur.id = :role_id and (" +
            "u.username like concat('%',:searchString,'%') " +
            "or u.name like concat('%',:searchString,'%')" +
            "or u.surname like concat('%',:searchString,'%')" +
            "or u.secondName like concat('%',:searchString,'%'))"

    )
    Page<UserEntity> findUserEntitiesByAllParameter(@Param("searchString") String searchString,
                                                    @Param("library_id") int library_id,
                                                    @Param("role_id") int role_id, Pageable pageable);


}
