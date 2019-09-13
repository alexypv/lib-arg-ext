package su.opencode.library.web.controllers;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import su.opencode.library.web.model.entities.UserImageEntity;
import su.opencode.library.web.secure.JwtUser;
import su.opencode.library.web.service.book.BookService;
import su.opencode.library.web.service.catalog.CatalogService;
import su.opencode.library.web.service.library.LibraryService;
import su.opencode.library.web.service.order.OrdersService;
import su.opencode.library.web.service.roles.RolesService;
import su.opencode.library.web.service.ticket.TicketService;
import su.opencode.library.web.service.user.UserService;

@Controller
public class PageDispatcherController extends BaseController {


    public PageDispatcherController(BookService bookService, RolesService rolesService, UserService userService, CatalogService catalogService, OrdersService ordersService, TicketService ticketService, LibraryService libraryService) {
        super(bookService, rolesService, userService, catalogService, ordersService, ticketService, libraryService);

    }

    @RequestMapping(method = RequestMethod.GET, value = {"/", "/index", "/login"})
    public String welcome() {
        return "login";
    }

    @RequestMapping("/directory")
    public String directory() {
        return "directory";
    }

    @RequestMapping("/users")
    public ModelAndView users() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");
        String username = jwtUser.getUsername();
        modelAndView.addObject("username", username);
        modelAndView.addObject("currentLibrary", jwtUser.getLibrary_id());
        return modelAndView;
    }


    @RequestMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        String username = jwtUser.getUsername();
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    @RequestMapping("/profile")
    public ModelAndView profile() {
        try {
            ModelAndView modelAndView = new ModelAndView();
            UserImageEntity userImageEntity = userService.getImage(jwtUser.getId());
            modelAndView.addObject("username", jwtUser.getUsername());
            modelAndView.addObject("name", jwtUser.getName());
            modelAndView.addObject("surname", jwtUser.getSurname());
            modelAndView.addObject("secondname", jwtUser.getSecondname());
            modelAndView.addObject("role", jwtUser.getAuthorities().toString());
            if (userImageEntity != null) {
                String imageData = new String(Base64.encodeBase64(userImageEntity.getData()));
                modelAndView.addObject("image", "data:image/png;base64," + imageData);
                return modelAndView;
            } else {
                modelAndView.addObject("image", "resources/img/icon/noimage.png");
                return modelAndView;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
