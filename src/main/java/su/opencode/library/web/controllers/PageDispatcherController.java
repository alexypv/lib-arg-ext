package su.opencode.library.web.controllers;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import su.opencode.library.web.model.entities.UserImageEntity;
import su.opencode.library.web.secure.JwtUser;
import su.opencode.library.web.service.book.BookService;
import su.opencode.library.web.service.user.UserService;

@Controller
public class PageDispatcherController {
    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public PageDispatcherController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, value = {"/", "/index", "/login"})
    public String welcome() {
        return "login";
    }

    @RequestMapping("/directory")
    public String directory() {
        return "directory";
    }

    @RequestMapping("/administration")
    public String administration() {
        return "administration";
    }


    @RequestMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        modelAndView.setViewName("home");
        String username = jwtUser.getUsername();
        modelAndView.addObject("username", username);
        return modelAndView;
    }

    @RequestMapping("/profile")
    public ModelAndView profile() {
        try {
            JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
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
