package su.opencode.library.web.service.roles;

import org.springframework.ui.ModelMap;
import su.opencode.library.web.model.entities.RoleEntity;

public interface RolesService {

    ModelMap getAllRoles();
    RoleEntity getRoleByName(String name);

}
