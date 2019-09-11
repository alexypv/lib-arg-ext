package su.opencode.library.web.controllers.api.roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import su.opencode.library.web.model.entities.UserEntity;
import su.opencode.library.web.service.roles.RolesService;
import su.opencode.library.web.service.ticket.TicketService;

@RestController
public class RolesApiController {

    private final RolesService rolesService;
    private final TicketService ticketService;

    @Autowired
    public RolesApiController(RolesService rolesService, TicketService ticketService) {
        this.rolesService = rolesService;
        this.ticketService = ticketService;
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
