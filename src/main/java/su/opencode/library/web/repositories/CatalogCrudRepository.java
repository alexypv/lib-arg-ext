package su.opencode.library.web.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import su.opencode.library.web.model.entities.CatalogEntity;
import su.opencode.library.web.model.entities.LibraryEntity;

import java.util.List;

@Repository
public interface CatalogCrudRepository extends CrudRepository<CatalogEntity, Integer> {

    List<CatalogEntity> findCatalogEntityByLibraryEntity(LibraryEntity library);
}


