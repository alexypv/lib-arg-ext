package su.opencode.library.web.controllers.api.roles;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import su.opencode.library.web.controllers.BaseController;
import su.opencode.library.web.model.entities.UserEntity;
import su.opencode.library.web.secure.JwtUser;
import su.opencode.library.web.service.book.BookService;
import su.opencode.library.web.service.catalog.CatalogService;
import su.opencode.library.web.service.library.LibraryService;
import su.opencode.library.web.service.order.OrdersService;
import su.opencode.library.web.service.roles.RolesService;
import su.opencode.library.web.service.ticket.TicketService;
import su.opencode.library.web.service.user.UserService;

@RestController
public class RolesApiController extends BaseController {


    public RolesApiController(BookService bookService, RolesService rolesService, UserService userService, CatalogService catalogService, OrdersService ordersService, TicketService ticketService, LibraryService libraryService) {
        super(bookService, rolesService, userService, catalogService, ordersService, ticketService, libraryService);
    }

    @RequestMapping(value = "/api/roles/getRoles", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getBooksInCatalog() {
        try {

            return new ResponseEntity<>(rolesService.getAllRoles(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ModelMap map = new ModelMap();
            map.addAttribute("Ошибка : ", e.getStackTrace());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/api/roles/getTicket", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getTicket(@RequestParam("id") int id) {
        UserEntity userEntity = new UserEntity(id);
            return new ResponseEntity<>(ticketService.getTicketByUser(userEntity), HttpStatus.OK);
    }

}
