package su.opencode.library.web.service.catalog;

import org.springframework.stereotype.Service;
import su.opencode.library.web.model.entities.BookEntity;
import su.opencode.library.web.model.entities.CatalogEntity;
import su.opencode.library.web.model.entities.LibraryEntity;
import su.opencode.library.web.model.entities.UserEntity;
import su.opencode.library.web.repositories.*;
import su.opencode.library.RepositoriesService;
import su.opencode.library.web.utils.JsonObject.CatalogJson;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogServiceImpl extends RepositoriesService implements CatalogService {

    public CatalogServiceImpl(BookCrudRepository bookRepository, CatalogCrudRepository catalogRepository, LibraryCrudRepository libraryRepository, OrderPositionCrudRepository orderPositionRepository, OrdersCrudRepository ordersRepository, RoleCrudRepository roleRepository, TicketCrudRepository ticketRepository, UserCrudRepository userRepository, UserImageCrudRepository userImageRepository) {
        super(bookRepository, catalogRepository, libraryRepository, orderPositionRepository, ordersRepository, roleRepository, ticketRepository, userRepository, userImageRepository);
    }

    @Override
    public List<CatalogJson> getCatalogsByLibraryId(int library_id) {
        List<CatalogEntity> catalogsList = catalogRepository.findCatalogEntityByLibraryEntity(new LibraryEntity(library_id));
        List<CatalogJson> result = new ArrayList<>();
        for (int count = 0; count < catalogsList.size(); count++) {
            CatalogJson catalogJson = new CatalogJson();
            catalogJson.setId(catalogsList.get(count).getId());
            catalogJson.setName(catalogsList.get(count).getName());
            result.add(catalogJson);
        }
        return result;
    }

    @Override
    public CatalogEntity getCatalogById(int id) {
        return catalogRepository.findById(id).get();
    }


    @Override
    public void createCatalog(String name, int library_id, int creator_id) {
        catalogRepository.save(new CatalogEntity(name, new LibraryEntity(library_id), new UserEntity(creator_id)));
    }

    @Override
    public void editCatalog(int id, String newCatalogName, int updater_id) {
        catalogRepository.findById(id).get().setName(newCatalogName);
        catalogRepository.findById(id).get().setAuditParamsForUpdate(new UserEntity(updater_id));
    }


    @Override
    public void deleteCatalogs(int[] catalogId) {
        for (int id : catalogId) {
            List<BookEntity> list = bookRepository.findListBookEntityByCatalogEntity(getCatalogById(id));
            for (int count = 0; count < list.size(); count++) {
                //отвязываю книжку
                list.get(count).setCatalogEntity(null);
            }
            // теперь удаляю каталог
            catalogRepository.deleteById(id);
        }
    }

}
