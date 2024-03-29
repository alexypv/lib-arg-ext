package su.opencode.library.web.controllers;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import su.opencode.library.web.model.entities.UserImageEntity;
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
    public ModelAndView directory() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("directory");
        String username = getJwtUser().getUsername();
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    @RequestMapping("/users")
    public ModelAndView users() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");
        String username = getJwtUser().getUsername();
        modelAndView.addObject("username", username);
        modelAndView.addObject("currentLibrary", getJwtUser().getLibrary_id());
        return modelAndView;
    }


    @RequestMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        String username = getJwtUser().getUsername();
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    @RequestMapping("/profile")
    public ModelAndView profile() {
        try {
            ModelAndView modelAndView = new ModelAndView();
            UserImageEntity userImageEntity = userService.getImage(getJwtUser().getId());
            modelAndView.addObject("username", getJwtUser().getUsername());
            modelAndView.addObject("name", getJwtUser().getName());
            modelAndView.addObject("surname", getJwtUser().getSurname());
            modelAndView.addObject("secondname", getJwtUser().getSecondname());
            modelAndView.addObject("role", getJwtUser().getAuthorities().toString());
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

    @RequestMapping("/orders")
    public ModelAndView orders() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("orders");
        String username = getJwtUser().getUsername();
        modelAndView.addObject("username", username);
        modelAndView.addObject("libraryID", getJwtUser().getLibrary_id());
        return modelAndView;
    }
}
