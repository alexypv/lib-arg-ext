package su.opencode.library.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import su.opencode.library.web.model.entities.LibraryEntity;
import su.opencode.library.web.secure.JwtUser;
import su.opencode.library.web.service.library.LibraryService;
import su.opencode.library.web.service.user.UserService;

import java.util.ArrayList;
import java.util.List;


@RestController
public class AdministrationController {

    private final LibraryService libraryService;
    private final UserService userService;

    @Autowired
    public AdministrationController(LibraryService libraryService, UserService userService) {
        this.libraryService = libraryService;
        this.userService = userService;
    }

    @RequestMapping(value = "/users/loadLibrary", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<List> getLibrary() {
        try {
            JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            if (jwtUser.getAuthorities().toString().equals("ROLE_GLOBAL")) {
                return null;

            } else if ((jwtUser.getAuthorities().toString().equals("ROLE_ADMIN"))) {
                List<LibraryEntity> result = new ArrayList<>();
                result.add(libraryService.getLibraryById(jwtUser.getLibrary_id()));
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
