package su.opencode.library.web.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import su.opencode.library.web.model.entities.UserEntity;
import su.opencode.library.web.model.entities.UserImageEntity;

@Repository
public interface UserImageCrudRepository extends CrudRepository<UserImageEntity, Integer> {

    UserImageEntity findUserImageEntityByUserEntity(UserEntity userEntity);

    void deleteUserImageEntityByUserEntity(UserEntity userEntity);

}
