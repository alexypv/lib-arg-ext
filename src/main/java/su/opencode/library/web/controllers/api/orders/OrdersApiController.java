package su.opencode.library.web.controllers.api.orders;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import su.opencode.library.web.controllers.BaseController;
import su.opencode.library.web.model.entities.BookOrderEntity;
import su.opencode.library.web.model.entities.TicketEntity;
import su.opencode.library.web.secure.JwtUser;
import su.opencode.library.web.service.book.BookService;
import su.opencode.library.web.service.catalog.CatalogService;
import su.opencode.library.web.service.library.LibraryService;
import su.opencode.library.web.service.order.OrdersService;
import su.opencode.library.web.service.roles.RolesService;
import su.opencode.library.web.service.ticket.TicketService;
import su.opencode.library.web.service.user.UserService;
import su.opencode.library.web.utils.JsonObject.OrderJson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

@RestController
public class OrdersApiController extends BaseController {


    public OrdersApiController(BookService bookService, RolesService rolesService, UserService userService, CatalogService catalogService, OrdersService ordersService, TicketService ticketService, LibraryService libraryService) {
        super(bookService, rolesService, userService, catalogService, ordersService, ticketService, libraryService);
    }

    @RequestMapping(value = "/api/orders/createOrder", method = RequestMethod.POST)
    public ResponseEntity createOrder(
            @RequestParam("giveDate") String giveDate,
            @RequestParam("returnDate") String returnDate,
            @RequestParam("orderBooks") String[] books_id,
            @RequestParam("ticketCode") String ticketCode
    ) {
        try {
            TicketEntity ticketEntity = ticketService.findTicketByCode(ticketCode);
            int[] selectedBooks = Arrays.stream(books_id).mapToInt(Integer::parseInt).toArray();
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(giveDate);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(returnDate);
            String orderCode = ordersService.createOrder(selectedBooks, jwtUser.getId(), startDate, endDate, ticketEntity);
            return new ResponseEntity<>(orderCode, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ModelMap map = new ModelMap();
            map.addAttribute("errorMessage", e.getStackTrace());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/api/orders/getHistory/{pageNumber}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getOrdersByTicket(
            @RequestParam("ticketCode") String ticketCode,
            @PathVariable int pageNumber) {
        PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
        List<BookOrderEntity> ordersList = ordersService.getOrdersByTicket(ticketService.findTicketByCode(ticketCode), pageable).getContent();
        int countPage = ordersService.getOrdersByTicket(ticketService.findTicketByCode(ticketCode), pageable).getTotalPages();
        List<OrderJson> result = new ArrayList<>();
        IntStream.range(0, ordersList.size()).forEach(count -> {
            OrderJson orderJson = new OrderJson();
            result.add(orderJson.convertBooksOrderEntityToOrderJson(ordersList.get(count)));
        });
        ModelMap map = new ModelMap();
        map.addAttribute("ordersList", result);
        map.addAttribute("countPages", countPage);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


}
