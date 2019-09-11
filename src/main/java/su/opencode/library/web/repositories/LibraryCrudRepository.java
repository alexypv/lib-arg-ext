package su.opencode.library.web.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import su.opencode.library.web.model.entities.LibraryEntity;

@Repository
public interface LibraryCrudRepository extends CrudRepository<LibraryEntity, Integer> {
}
