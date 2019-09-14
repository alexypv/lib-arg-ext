package su.opencode.library.web.controllers.api.users;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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
import su.opencode.library.web.utils.JsonObject.UserJson;
import javax.xml.bind.DatatypeConverter;


@RestController
public class UsersApiController extends BaseController {


    public UsersApiController(BookService bookService, RolesService rolesService, UserService userService, CatalogService catalogService, OrdersService ordersService, TicketService ticketService, LibraryService libraryService) {
        super(bookService, rolesService, userService, catalogService, ordersService, ticketService, libraryService);

    }

    @RequestMapping(value = "/api/users/createUser", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity createUser(
            @RequestParam("newUsername") String username,
            @RequestParam("newPassword") String password,
            @RequestParam("newSurname") String surname,
            @RequestParam("newName") String name,
            @RequestParam("newSecondName") String secondname,
            @RequestParam("newRole") int role_id,
            @RequestParam("newLibrary") int library_id
    ) {

        try {
            PageRequest pageable = PageRequest.of(getJwtUser().getCurrentPage() - 1, 10);
            String newTicketCode = userService.createUser(username, password, surname, name, secondname, library_id, role_id, getJwtUser().getId(), getJwtUser().getAuthorities().toString());
            ModelMap map = userService.getUsersByRolesAndLibrary(role_id, pageable, getJwtUser().getLibrary_id());
            map.addAttribute("newTicketCode", newTicketCode);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        catch (AccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/api/users/getUsersInLibrary/{pageNumber}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getUsersByRoleAndJwtLib(
            @RequestParam int role_id,
            @PathVariable int pageNumber
    ) {
        getJwtUser().setCurrentPage(pageNumber);
        getJwtUser().setCurrentSection(String.valueOf(role_id));
        PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
        return new ResponseEntity<>(userService.getUsersByRolesAndLibrary(role_id, pageable, getJwtUser().getLibrary_id()), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/users/getUsers/{pageNumber}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getUsersByRoleAndLibrary(
            @RequestParam ("role_id") int role_id,
            @RequestParam ("library_id")int library_id,
            @PathVariable int pageNumber
    ) {

        getJwtUser().setCurrentPage(pageNumber);
        getJwtUser().setCurrentSection(String.valueOf(role_id));
        PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
        return new ResponseEntity<>(userService.getUsersByRolesAndLibrary(role_id, pageable, library_id), HttpStatus.OK);
    }


    @RequestMapping(value = "/api/users/getUserInfo/{id}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getUserInfo(
            @PathVariable int id
    ) {

        UserJson user = new UserJson();
        UserEntity userEntity = userService.getUserById(id);
        user = user.convertUserEntityToUserJson(userEntity);
        try {
            user.setTicketCode(ticketService.getTicketByUser(userEntity).getCode());
        } catch (NullPointerException e) {
            user.setTicketCode("-");
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/users/uploadImage", method = RequestMethod.POST, produces = "application/octet-stream;base64")
    public ResponseEntity uploadProfileImage(@RequestParam("imageBase64") String file) {
        try {

            byte[] decodedFile = DatatypeConverter.parseBase64Binary(file.replaceAll("data:image/.+;base64,", ""));
            userService.uploadImage(getJwtUser().getId(), decodedFile);
            return new ResponseEntity<>("Successfull", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Bad", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/api/users/deleteImage")
    public ResponseEntity deleteUserImage() {
        try {
            userService.deleteUserImage(getJwtUser().getId());
            return new ResponseEntity<>("Successful delete!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "api/users/changeUserInfo/{getPageUser}")
    public ResponseEntity editUser(
            @PathVariable boolean getPageUser,
            @RequestParam("username") String username,
            @RequestParam("surname") String surname,
            @RequestParam("name") String name,
            @RequestParam("secondname") String secondname,
            @RequestParam("password") String password,
            @RequestParam("user_id") int user_id) {

        try {
            userService.changeUserInfo(user_id, username, password, surname, name, secondname, getJwtUser());
            UserEntity userEntity = userService.getUserById(getJwtUser().getId());
            getJwtUser().setSurname(userEntity.getSurname());
            getJwtUser().setUsername(userEntity.getUsername());
            getJwtUser().setName(userEntity.getName());
            getJwtUser().setSecondname(userEntity.getSecondName());

            if (getPageUser) {
                PageRequest pageable = PageRequest.of(getJwtUser().getCurrentPage() - 1, 10);
                return new ResponseEntity<>(userService.getUsersByRolesAndLibrary(Integer.valueOf(getJwtUser().getCurrentSection()), pageable, getJwtUser().getLibrary_id()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User information has been edited successfully.", HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
