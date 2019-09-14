package su.opencode.library.web.service.ticket;

import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;
import su.opencode.library.web.model.entities.TicketEntity;
import su.opencode.library.web.model.entities.UserEntity;

public interface TicketService {

    TicketEntity getTicketByUser(UserEntity userEntity);

    ModelMap findTicketByCodeContains(String code, Pageable pageable);

    TicketEntity findTicketByCode(String code);

    String createTicket (UserEntity userEntity, UserEntity creator);

}
