package su.opencode.library.web.controllers.api.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import su.opencode.library.web.controllers.BaseController;
import su.opencode.library.web.controllers.api.orders.OrdersApiController;
import su.opencode.library.web.controllers.api.users.UsersApiController;
import su.opencode.library.web.model.entities.BookOrderEntity;
import su.opencode.library.web.model.entities.UserEntity;
import su.opencode.library.web.secure.JwtUser;
import su.opencode.library.web.service.book.BookService;
import su.opencode.library.web.service.catalog.CatalogService;
import su.opencode.library.web.service.library.LibraryService;
import su.opencode.library.web.service.order.OrdersService;
import su.opencode.library.web.service.order.OrdersServiceImpl;
import su.opencode.library.web.service.roles.RolesService;
import su.opencode.library.web.service.ticket.TicketService;
import su.opencode.library.web.service.user.UserService;
import su.opencode.library.web.utils.JsonObject.OrderJson;
import su.opencode.library.web.utils.JsonObject.UserJson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RestController
public class SearchApiController extends BaseController {

private final OrdersApiController ordersApiController;
private final UsersApiController usersApiController;

    public SearchApiController(BookService bookService, RolesService rolesService, UserService userService, CatalogService catalogService, OrdersService ordersService, TicketService ticketService, LibraryService libraryService, OrdersApiController ordersApiController, UsersApiController usersApiController) {
        super(bookService, rolesService, userService, catalogService, ordersService, ticketService, libraryService);
        this.ordersApiController = ordersApiController;
        this.usersApiController = usersApiController;

    }

    @RequestMapping(value = "/api/search/findTicketByCode", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getBooksInCatalog(@RequestParam("code") String code,
                                            @RequestParam("pageNumber") int pageNumber) {
        try {
            PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
            return new ResponseEntity<>(ticketService.findTicketByCodeContains(code, pageable), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ModelMap map = new ModelMap();
            map.addAttribute("Ошибка : ", e.getStackTrace());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/api/search/findOrder/{pageNumber}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity findOrderByAllCriteria(
            @RequestParam("searchValue") String searchValue,
            @RequestParam("ticketCode") String ticketCode,
            @PathVariable int pageNumber) {
        if (searchValue.isEmpty()) {

            return ordersApiController.getOrdersByTicket(ticketCode, pageNumber);
        } else {
            PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
            Page<BookOrderEntity> page = ordersService.searchOrder(searchValue, ticketCode, pageable);
            List<BookOrderEntity> ordersList = page.getContent();
            int countPage = page.getTotalPages();
            return OrdersApiController.returnOrderResponse(ordersList, countPage);
        }
    }

    @RequestMapping(value = "/api/search/findUser/{pageNumber}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity findUsersByAllCriteria(
            @RequestParam("searchValue") String searchValue,
            @RequestParam("libraryId") int library_id,
            @RequestParam("roleId") int role_id,
            @PathVariable int pageNumber) {
        if (searchValue.isEmpty()) {
            return usersApiController.getUsersByRoleAndLibrary(role_id, library_id, pageNumber);
        } else {
            PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
            Page<UserEntity> page = userService.searchUser(searchValue, library_id, role_id, pageable);
            List<UserEntity> usersList = page.getContent();
            int countPage = page.getTotalPages();
            List<UserJson> result = new ArrayList<>();
            IntStream.range(0, usersList.size()).forEach(count -> {
                UserJson userJson = new UserJson();
                result.add(userJson.convertUserEntityToUserJson(usersList.get(count)));
            });
            ModelMap map = new ModelMap();
            map.addAttribute("usersList", result);
            map.addAttribute("countPages", countPage);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

}
