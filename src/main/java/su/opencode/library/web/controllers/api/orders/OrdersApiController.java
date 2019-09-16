package su.opencode.library.web.controllers.api.orders;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import su.opencode.library.web.controllers.BaseController;
import su.opencode.library.web.model.entities.*;
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
            LibraryEntity libraryEntity = libraryService.getLibraryById(getJwtUser().getLibrary_id());
            int[] selectedBooks = Arrays.stream(books_id).mapToInt(Integer::parseInt).toArray();
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(giveDate);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(returnDate);
            String orderCode = ordersService.createOrder(selectedBooks, getJwtUser().getId(), startDate, endDate, ticketEntity, libraryEntity);
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
        return returnOrderResponse(ordersList, countPage);
    }

    @RequestMapping(value = "/api/orders/getOrders/{pageNumber}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getOrdersByLibrary(
            @RequestParam("libraryID") String libraryID,
            @PathVariable int pageNumber) {
        PageRequest pageable = PageRequest.of(pageNumber - 1, 17);
        ModelMap map = new ModelMap();
        try {
            if (!libraryID.equals("")) {
                Page<BookOrderEntity> list = ordersService.getOrdersByLibrary(new LibraryEntity(Integer.valueOf(libraryID)), pageable);
                List<BookOrderEntity> result = list.getContent();
                int countPages = list.getTotalPages();
                return returnOrderResponse(result, countPages);
            } else throw new NullPointerException();
        } catch (NullPointerException e) {
            map.addAttribute("error", "Parameter 'libraryID' is not present");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/api/orders/getAll/{pageNumber}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getAllOrders(
            @PathVariable int pageNumber) {
        PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
        ModelMap map = new ModelMap();
        try {
            map.addAttribute("ordersList", ordersService.getAllOrders(pageable));
            return new ResponseEntity<>(map, HttpStatus.OK);

        } catch (Exception e) {
            map.addAttribute("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/api/orders/returnOrder", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity returnOrder(
            @RequestParam("orderID") int orderID) {
        try {
            UserEntity returner = userService.getUserById(getJwtUser().getId());
            ordersService.returnOrder(orderID, returner);
            return new ResponseEntity<>("Successful return!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public static ResponseEntity returnOrderResponse(List<BookOrderEntity> ordersList, int countPage) {
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
