package su.opencode.library.web.controllers.api.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import su.opencode.library.web.service.ticket.TicketService;

@RestController
public class SearchApiController {

    private final TicketService ticketService;

    @Autowired
    public SearchApiController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @RequestMapping(value = "/api/search/findTicketByCode", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getBooksInCatalog(@RequestParam("code") String code,
                                            @RequestParam("pageNumber") int pageNumber) {
        try {
            PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
            return new ResponseEntity<>(ticketService.findTicketByCodeContains(code, pageable), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ModelMap map = new ModelMap();
            map.addAttribute("Ошибка : ", e.getStackTrace());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
