package su.opencode.library.web.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import su.opencode.library.web.model.entities.BookOrderEntity;
import su.opencode.library.web.model.entities.TicketEntity;

@Repository
public interface OrdersCrudRepository extends CrudRepository<BookOrderEntity, Integer> {

    Page<BookOrderEntity> findBookOrderEntitiesByTicketEntity(TicketEntity ticketEntity, Pageable pageable);

      @Query("select o from BookOrderEntity o where " +
            "o.ticketEntity.code = :ticketCode and (" +
            "o.code like concat('%',:searchString,'%') " +
            "or o.startDate like concat('%',:searchString,'%')" +
            "or o.endDate like concat('%',:searchString,'%')" +
            "or o.realReturnDate like concat('%',:searchString,'%')"+
            "or o.id like concat('%',:searchString,'%')) "

    )
    Page<BookOrderEntity> findBookEntitiesByAllParameter(@Param("searchString") String searchString,
                                                         @Param("ticketCode") String ticketCode, Pageable pageable);

}
