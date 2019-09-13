package su.opencode.library.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import su.opencode.library.web.secure.JwtUser;
import su.opencode.library.web.service.book.BookService;
import su.opencode.library.web.service.catalog.CatalogService;
import su.opencode.library.web.service.library.LibraryService;
import su.opencode.library.web.service.order.OrdersService;
import su.opencode.library.web.service.roles.RolesService;
import su.opencode.library.web.service.ticket.TicketService;
import su.opencode.library.web.service.user.UserService;

@Controller
public class BaseController {
    protected JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    protected final BookService bookService;
    protected final RolesService rolesService;
    protected final UserService userService;
    protected final CatalogService catalogService;
    protected final OrdersService ordersService;
    protected final TicketService ticketService;
    protected final LibraryService libraryService;

    @Autowired
    public BaseController(BookService bookService, RolesService rolesService, UserService userService, CatalogService catalogService, OrdersService ordersService, TicketService ticketService, LibraryService libraryService) {
        this.bookService = bookService;
        this.rolesService = rolesService;
        this.userService = userService;
        this.catalogService = catalogService;
        this.ordersService = ordersService;
        this.ticketService = ticketService;
        this.libraryService = libraryService;
    }
}
