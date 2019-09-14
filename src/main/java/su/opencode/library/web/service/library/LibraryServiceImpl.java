package su.opencode.library.web.service.library;

import org.springframework.stereotype.Service;
import su.opencode.library.web.model.entities.LibraryEntity;
import su.opencode.library.web.model.entities.UserEntity;
import su.opencode.library.web.repositories.*;
import su.opencode.library.RepositoriesService;
import su.opencode.library.web.utils.IteratorUtils;

import java.util.List;
import java.util.Objects;

@Service
public class LibraryServiceImpl extends RepositoriesService implements LibraryService {

    public LibraryServiceImpl(BookCrudRepository bookRepository, CatalogCrudRepository catalogRepository, LibraryCrudRepository libraryRepository, OrderPositionCrudRepository orderPositionRepository, OrdersCrudRepository ordersRepository, RoleCrudRepository roleRepository, TicketCrudRepository ticketRepository, UserCrudRepository userRepository, UserImageCrudRepository userImageRepository) {
        super(bookRepository, catalogRepository, libraryRepository, orderPositionRepository, ordersRepository, roleRepository, ticketRepository, userRepository, userImageRepository);
    }

    /**
     * @param id ID библиотеки
     * @return библиотека LibraryEntity.
     */
    @Override
    public LibraryEntity getLibraryById(int id) {
        if (libraryRepository.existsById(id)) {
            return libraryRepository.findById(id).get();
        } else return null;
    }

    @Override
    public void createLibrary(String name, UserEntity creator) {
        libraryRepository.save(new LibraryEntity(name, creator));
    }

    /**
     * @return List<Librarys> список всех библиотек в системе
     * Используется только у глобального админа при перемещении пользователя из одной библиотеки в другую
     */
    @Override
    public List<LibraryEntity> getAllLibrary() {
        return IteratorUtils.toList(libraryRepository.findAll());
    }

    @Override
    public void editLibrary(int id, String newName) {
        if (libraryRepository.existsById(id)) {
            Objects.requireNonNull(libraryRepository.findById(id).orElse(null)).setName(newName);
        }
    }

    @Override
    public void removeLibrary(int[] id) {
        for (int i : id) {
            libraryRepository.deleteById(i);
        }
    }
}
