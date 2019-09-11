package su.opencode.library.web.service.roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import su.opencode.library.web.model.entities.RoleEntity;
import su.opencode.library.web.repositories.RoleCrudRepository;
import su.opencode.library.web.utils.IteratorUtils;
import su.opencode.library.web.utils.JsonObject.RoleJson;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolesServiceImpl implements RolesService {

    private final RoleCrudRepository roleRepository;

    @Autowired
    public RolesServiceImpl(RoleCrudRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public ModelMap getAllRoles() {
        List<RoleEntity> rolesList = IteratorUtils.toList(roleRepository.findAll());
        List<RoleJson> result = new ArrayList<>();
        ModelMap map = new ModelMap();
        for (int count = 0; count < rolesList.size(); count++) {
            RoleJson roleJson = new RoleJson();
            result.add(roleJson.convertRoleEntityToRoleJson(rolesList.get(count)));
        }
        map.addAttribute("rolesList", result);
        return map;
    }

    @Override
    public RoleEntity getRoleByName(String name) {
        return roleRepository.findRoleEntitiesByName(name);
    }
}
