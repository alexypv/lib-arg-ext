package su.opencode.library.web.controllers.api.catalogs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import su.opencode.library.web.controllers.BaseController;
import su.opencode.library.web.secure.JwtUser;
import su.opencode.library.web.service.book.BookService;
import su.opencode.library.web.service.catalog.CatalogService;
import su.opencode.library.web.service.library.LibraryService;
import su.opencode.library.web.service.order.OrdersService;
import su.opencode.library.web.service.roles.RolesService;
import su.opencode.library.web.service.ticket.TicketService;
import su.opencode.library.web.service.user.UserService;

import java.util.Arrays;
import java.util.List;

@RestController
public class CatalogsApiController extends BaseController {


    public CatalogsApiController(BookService bookService, RolesService rolesService, UserService userService, CatalogService catalogService, OrdersService ordersService, TicketService ticketService, LibraryService libraryService) {
        super(bookService, rolesService, userService, catalogService, ordersService, ticketService, libraryService);
    }

    @RequestMapping(value = "/api/catalogs/loadCatalogs", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<List> getCatalogs() {
        return new ResponseEntity<>(catalogService.getCatalogsByLibraryId(getJwtUser().getLibrary_id()), HttpStatus.OK);
    }


    @RequestMapping(value = "/api/catalogs/createCatalog", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<List> createCatalog(@RequestParam("newCatalogName") String newCatalogName) {
        catalogService.createCatalog(newCatalogName, getJwtUser().getLibrary_id(), getJwtUser().getId());
        return new ResponseEntity<>(catalogService.getCatalogsByLibraryId(getJwtUser().getLibrary_id()), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/catalogs/deleteCatalog", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<List> deleteCatalog(@RequestParam("catalogsChoosenList") String[] catalogs_id) {
        int[] removableId = Arrays.stream(catalogs_id).mapToInt(Integer::parseInt).toArray();
        catalogService.deleteCatalogs(removableId);
        return new ResponseEntity<>(catalogService.getCatalogsByLibraryId(getJwtUser().getLibrary_id()), HttpStatus.OK);
    }


}
