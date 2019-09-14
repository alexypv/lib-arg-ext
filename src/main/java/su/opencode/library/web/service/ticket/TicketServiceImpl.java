package su.opencode.library.web.service.ticket;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import su.opencode.library.web.model.entities.TicketEntity;
import su.opencode.library.web.model.entities.UserEntity;
import su.opencode.library.web.repositories.*;
import su.opencode.library.RepositoriesService;
import su.opencode.library.web.utils.CodeGenerator;
import su.opencode.library.web.utils.JsonObject.TicketJson;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketServiceImpl extends RepositoriesService implements TicketService {

    public TicketServiceImpl(BookCrudRepository bookRepository, CatalogCrudRepository catalogRepository, LibraryCrudRepository libraryRepository, OrderPositionCrudRepository orderPositionRepository, OrdersCrudRepository ordersRepository, RoleCrudRepository roleRepository, TicketCrudRepository ticketRepository, UserCrudRepository userRepository, UserImageCrudRepository userImageRepository) {
        super(bookRepository, catalogRepository, libraryRepository, orderPositionRepository, ordersRepository, roleRepository, ticketRepository, userRepository, userImageRepository);
    }

    @Override
    public TicketEntity getTicketByUser(UserEntity userEntity) {
        return ticketRepository.findTicketEntityByUserEntity(userEntity);
    }

    @Override
    public ModelMap findTicketByCodeContains(String code, Pageable pageable) {
        ModelMap map = new ModelMap();
        List<TicketEntity> ticketList = ticketRepository.findPageTicketEntityByCodeContaining(code, pageable).getContent();
        List<TicketJson> result = new ArrayList<>();
        int count = 0;
        TicketJson ticketJson = new TicketJson();
        while (count < ticketList.size()) {
            result.add(ticketJson.convertTicketEntityToTicketJson(ticketList.get(count)));
            count++;
        }
        map.addAttribute("ticketList", result);
        return map;
    }

    @Override
    public TicketEntity findTicketByCode(String code) {
        return ticketRepository.findTicketEntityByCode(code);
    }

    @Override
    public String createTicket(UserEntity userEntity, UserEntity creator) {
        CodeGenerator generator = new CodeGenerator();
        String code = generator.generateTicketNumber(userEntity);
        TicketEntity ticket = new TicketEntity(code, userEntity, creator);
        ticketRepository.save(ticket);
        return ticket.getCode();
    }
}
