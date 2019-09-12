package su.opencode.library.web.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;
import su.opencode.library.web.model.entities.UserEntity;
import su.opencode.library.web.model.entities.UserImageEntity;
import su.opencode.library.web.secure.JwtUser;


public interface UserService {

    UserEntity getUserById(int user_id);

    UserEntity getUserByUsername(String username);

    String createUser(String username, String password, String surname, String name, String secondName, int library_id, int role_id, int creator_id);

    void uploadImage(int user_id, byte[] file);

    UserImageEntity getImage(int user_id);

    void deleteUserImage(int user_id);

    void changeUserInfo(int id, String username, String password, String surname, String name, String secondname, JwtUser jwtUser);

    ModelMap getUsersByRolesAndLibrary(int role_id, Pageable pageable , int library_id);

    Page<UserEntity> searchUser(String searchValue, int library_id, int role_id, Pageable pageable);
}
