package su.opencode.library.web.controllers.api.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import su.opencode.library.web.model.entities.UserEntity;
import su.opencode.library.web.secure.JwtUser;
import su.opencode.library.web.service.ticket.TicketService;
import su.opencode.library.web.service.user.UserService;
import su.opencode.library.web.utils.JsonObject.UserJson;

import javax.xml.bind.DatatypeConverter;

@RestController
public class UsersApiController {

    private final UserService userService;
    private final TicketService ticketService;

    @Autowired
    public UsersApiController(UserService userService, TicketService ticketService) {
        this.userService = userService;
        this.ticketService = ticketService;
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
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        try {
            PageRequest pageable = PageRequest.of(jwtUser.getCurrentPage() - 1, 15);
            String newTicketCode = userService.createUser(username, password, surname, name, secondname, library_id, role_id, jwtUser.getId());
            ModelMap map = userService.getUsersByRolesAndLibrary(role_id, pageable, jwtUser.getLibrary_id());
            map.addAttribute("newTicketCode", newTicketCode);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getStackTrace(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/api/users/getUsers/{role_id}/{pageNumber}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getUsersByRoleAndLibrary(
            @PathVariable int role_id,
            @PathVariable int pageNumber
    ) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        jwtUser.setCurrentPage(pageNumber);
        jwtUser.setCurrentSection(String.valueOf(role_id));
        PageRequest pageable = PageRequest.of(pageNumber - 1, 15);
        return new ResponseEntity<>(userService.getUsersByRolesAndLibrary(role_id, pageable, jwtUser.getLibrary_id()), HttpStatus.OK);
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

    @RequestMapping(value = "/api/users/uploadImage", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity uploadProfileImage(@RequestParam("imageBase64") String file) {
        try {

            JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            byte[] decodedFile = DatatypeConverter.parseBase64Binary(file.replaceAll("data:image/.+;base64,", ""));
            userService.uploadImage(jwtUser.getId(), decodedFile);
            return new ResponseEntity<>("Successfull", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Bad", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/api/users/deleteImage")
    public ResponseEntity deleteUserImage() {
        try {
            JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            userService.deleteUserImage(jwtUser.getId());
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

        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        try {
            userService.changeUserInfo(user_id, username, password, surname, name, secondname, jwtUser);
            UserEntity userEntity = userService.getUserById(jwtUser.getId());
            jwtUser.setSurname(userEntity.getSurname());
            jwtUser.setUsername(userEntity.getUsername());
            jwtUser.setName(userEntity.getName());
            jwtUser.setSecondname(userEntity.getSecondName());

            if (getPageUser) {
                PageRequest pageable = PageRequest.of(jwtUser.getCurrentPage() - 1, 15);
                return new ResponseEntity<>(userService.getUsersByRolesAndLibrary(Integer.valueOf(jwtUser.getCurrentSection()), pageable, jwtUser.getLibrary_id()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User information has been edited successfully.", HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
