package su.opencode.library;

import org.springframework.beans.factory.annotation.Autowired;
import su.opencode.library.web.repositories.*;

public class RepositoriesService {

    protected final BookCrudRepository bookRepository;
    protected final CatalogCrudRepository catalogRepository;
    protected final LibraryCrudRepository libraryRepository;
    protected final OrderPositionCrudRepository orderPositionRepository;
    protected final OrdersCrudRepository ordersRepository;
    protected final RoleCrudRepository roleRepository;
    protected final TicketCrudRepository ticketRepository;
    protected final UserCrudRepository userRepository;
    protected final UserImageCrudRepository userImageRepository;

    @Autowired
    public RepositoriesService(BookCrudRepository bookRepository, CatalogCrudRepository catalogRepository, LibraryCrudRepository libraryRepository, OrderPositionCrudRepository orderPositionRepository, OrdersCrudRepository ordersRepository, RoleCrudRepository roleRepository, TicketCrudRepository ticketRepository, UserCrudRepository userRepository, UserImageCrudRepository userImageRepository) {
        this.bookRepository = bookRepository;
        this.catalogRepository = catalogRepository;
        this.libraryRepository = libraryRepository;
        this.orderPositionRepository = orderPositionRepository;
        this.ordersRepository = ordersRepository;
        this.roleRepository = roleRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.userImageRepository = userImageRepository;
    }
}
