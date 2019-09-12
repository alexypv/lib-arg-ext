package su.opencode.library.web.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import su.opencode.library.web.model.entities.BookEntity;
import su.opencode.library.web.model.entities.CatalogEntity;
import su.opencode.library.web.model.entities.LibraryEntity;

import java.util.List;

@Repository
public interface BookCrudRepository extends CrudRepository<BookEntity, Integer> {


    //Для всех книжек
    Page<BookEntity> findPageBookEntityByLibraryEntity(LibraryEntity library, Pageable pageable);

    //Для книжек в библиотеке, но с пустым каталогом
    Page<BookEntity> findPageBookEntityByLibraryEntityAndCatalogEntity(LibraryEntity library, CatalogEntity catalogEntity, Pageable pageable);

    //Для книг в каком-то конкретном каталоге
    Page<BookEntity> findPageBookEntityByCatalogEntity(CatalogEntity catalogEntity, Pageable pageable);

    //Получить лист всег книг в каталоге
    List<BookEntity> findListBookEntityByCatalogEntity(CatalogEntity catalogEntity);

    Long countBookEntitiesByCatalogEntity(CatalogEntity catalogEntity);

    @Query("select b from BookEntity b where b.id in " +
            "(select o.bookEntity.id from OrderPositionEntity o where o.bookOrderEntity.id = :orderId)")
    Page<BookEntity> getBooksInOrder(@Param("orderId") int orderID, Pageable pageable);

}
