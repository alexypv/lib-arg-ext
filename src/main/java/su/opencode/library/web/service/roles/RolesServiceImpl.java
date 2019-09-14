package su.opencode.library.web.service.roles;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import su.opencode.library.web.model.entities.RoleEntity;
import su.opencode.library.web.model.entities.UserEntity;
import su.opencode.library.web.repositories.*;
import su.opencode.library.RepositoriesService;
import su.opencode.library.web.utils.IteratorUtils;
import su.opencode.library.web.utils.JsonObject.RoleJson;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolesServiceImpl extends RepositoriesService implements RolesService {

    public RolesServiceImpl(BookCrudRepository bookRepository, CatalogCrudRepository catalogRepository, LibraryCrudRepository libraryRepository, OrderPositionCrudRepository orderPositionRepository, OrdersCrudRepository ordersRepository, RoleCrudRepository roleRepository, TicketCrudRepository ticketRepository, UserCrudRepository userRepository, UserImageCrudRepository userImageRepository) {
        super(bookRepository, catalogRepository, libraryRepository, orderPositionRepository, ordersRepository, roleRepository, ticketRepository, userRepository, userImageRepository);
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

    @Override
    public void createRole(String name) {
        RoleEntity roleEntity = new RoleEntity(name);
        roleRepository.save(roleEntity);
    }
}
