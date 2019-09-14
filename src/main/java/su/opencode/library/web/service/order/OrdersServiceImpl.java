package su.opencode.library.web.service.order;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import su.opencode.library.web.model.entities.*;
import su.opencode.library.web.repositories.*;
import su.opencode.library.RepositoriesService;
import su.opencode.library.web.utils.CodeGenerator;

import java.util.Date;

@Service
public class OrdersServiceImpl extends RepositoriesService implements OrdersService {

    public OrdersServiceImpl(BookCrudRepository bookRepository, CatalogCrudRepository catalogRepository, LibraryCrudRepository libraryRepository, OrderPositionCrudRepository orderPositionRepository, OrdersCrudRepository ordersRepository, RoleCrudRepository roleRepository, TicketCrudRepository ticketRepository, UserCrudRepository userRepository, UserImageCrudRepository userImageRepository) {
        super(bookRepository, catalogRepository, libraryRepository, orderPositionRepository, ordersRepository, roleRepository, ticketRepository, userRepository, userImageRepository);
    }

    /**
     * @param creator_id id создателя;
     * @param books_id
     */
    @Override
    public String createOrder(int[] books_id, int creator_id, Date giveDate, Date returnDate, TicketEntity ticketEntity) {
        CodeGenerator codeGenerator = new CodeGenerator();
        UserEntity creator = userRepository.findById(creator_id).orElse(null);
        //создаю пустой заказ
        BookOrderEntity order = new BookOrderEntity(codeGenerator.generateOrderNumber(), giveDate, returnDate, ticketEntity, creator);
        ordersRepository.save(order);
        for (int id : books_id) {
            BookEntity bookEntity = bookRepository.findById(id).get();
            orderPositionRepository.save(new OrderPositionEntity(order, bookEntity, creator));
            bookEntity.setAvailable(false);
            bookRepository.save(bookEntity);
        }
        return order.getCode();
    }

    @Override
    public Page<BookOrderEntity> getOrdersByTicket(TicketEntity ticketEntity, Pageable pageable) {
        return ordersRepository.findBookOrderEntitiesByTicketEntity(ticketEntity, pageable);
    }

    @Override
    public Page<BookOrderEntity> searchOrder(String searchValue, String ticketCode, Pageable pageable) {
        return ordersRepository.findBookEntitiesByAllParameter(searchValue, ticketCode, pageable);
    }
}
