package su.opencode.library;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import su.opencode.library.web.model.entities.LibraryEntity;
import su.opencode.library.web.model.entities.RoleEntity;
import su.opencode.library.web.model.entities.UserEntity;
import su.opencode.library.web.repositories.*;
import su.opencode.library.web.secure.JwtTokenProvider;
import su.opencode.library.web.service.library.LibraryService;
import su.opencode.library.web.service.roles.RolesService;
import su.opencode.library.web.service.ticket.TicketService;
import su.opencode.library.web.utils.IteratorUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class OnApplicationStartUp extends RepositoriesService {

    private final LibraryService libraryService;
    private final RolesService rolesService;
    private final TicketService ticketService;
    private final JwtTokenProvider jwtTokenProvider;

    public OnApplicationStartUp(BookCrudRepository bookRepository, CatalogCrudRepository catalogRepository, LibraryCrudRepository libraryRepository, OrderPositionCrudRepository orderPositionRepository, OrdersCrudRepository ordersRepository, RoleCrudRepository roleRepository, TicketCrudRepository ticketRepository, UserCrudRepository userRepository, UserImageCrudRepository userImageRepository, LibraryService libraryService, RolesService rolesService, JwtTokenProvider jwtTokenProvider, TicketService ticketService, JwtTokenProvider jwtTokenProvider1) {
        super(bookRepository, catalogRepository, libraryRepository, orderPositionRepository, ordersRepository, roleRepository, ticketRepository, userRepository, userImageRepository);
        this.libraryService = libraryService;
        this.rolesService = rolesService;
        this.ticketService = ticketService;
        this.jwtTokenProvider = jwtTokenProvider1;
    }


    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (IteratorUtils.toList(libraryRepository.findAll()).size() <= 0) {
            preloadData();
        }
    }

    private void preloadData() {
        libraryService.createLibrary("LocalLibrary", null);

        LibraryEntity libraryEntity = libraryRepository.findLibraryEntityByName("LocalLibrary");

        rolesService.createRole("ROLE_GLOBAL");
        List<RoleEntity> role = new ArrayList<>();
        role.add(rolesService.getRoleByName("ROLE_GLOBAL"));
        userRepository.save(new UserEntity(
                "admin",
                jwtTokenProvider.passwordEncoder().encode("1"),
                "Глобальный",
                "Администратор",
                "Библиотеки",
                libraryEntity,
                role, null));
        role.clear();
        UserEntity creator = userRepository.findUserEntityByUsername("admin");
        ticketService.createTicket(userRepository.findUserEntityByUsername("admin"), creator);

        rolesService.createRole("ROLE_ADMIN");
        role.add(rolesService.getRoleByName("ROLE_ADMIN"));
        userRepository.save(new UserEntity(
                "adm",
                jwtTokenProvider.passwordEncoder().encode("1"),
                "Локальный",
                "Администратор",
                "Библиотеки",
                libraryEntity,
                role,
                creator));
        role.clear();

        ticketService.createTicket(userRepository.findUserEntityByUsername("adm"), creator);

        rolesService.createRole("ROLE_LIBRARIER");
        role.add(rolesService.getRoleByName("ROLE_LIBRARIER"));
        userRepository.save(new UserEntity(
                "lib",
                jwtTokenProvider.passwordEncoder().encode("1"),
                "Локальный",
                "Библиотекарь",
                "Библиотеки",
                libraryEntity,
                role,
                creator));
        role.clear();
        ticketService.createTicket(userRepository.findUserEntityByUsername("lib"), creator);

        rolesService.createRole("ROLE_READER");
        role.add(rolesService.getRoleByName("ROLE_READER"));
        userRepository.save(new UserEntity(
                "usr",
                jwtTokenProvider.passwordEncoder().encode("1"),
                "Локальный",
                "Читатель",
                "Библиотеки",
                libraryEntity,
                role,
                creator));
        role.clear();
        ticketService.createTicket(userRepository.findUserEntityByUsername("usr"), creator);

        libraryEntity.setAuditParamsForCreation(creator);
        libraryRepository.save(libraryEntity);

    }
}
