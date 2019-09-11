package su.opencode.library.web.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import su.opencode.library.web.model.entities.TicketEntity;
import su.opencode.library.web.model.entities.UserEntity;

@Repository
public interface TicketCrudRepository extends CrudRepository<TicketEntity, Integer> {

    TicketEntity findTicketEntityByUserEntity(UserEntity userEntity);

    Page<TicketEntity> findPageTicketEntityByCodeContaining(String code, Pageable pageable);

    TicketEntity findTicketEntityByCode(String code);
}
