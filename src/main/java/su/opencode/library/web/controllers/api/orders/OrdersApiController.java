package su.opencode.library.web.controllers.api.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import su.opencode.library.web.model.entities.BookOrderEntity;
import su.opencode.library.web.model.entities.TicketEntity;
import su.opencode.library.web.secure.JwtUser;
import su.opencode.library.web.service.order.OrdersService;
import su.opencode.library.web.service.ticket.TicketService;
import su.opencode.library.web.utils.JsonObject.OrderJson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

@RestController
public class OrdersApiController {

    private final OrdersService ordersService;
    private final TicketService ticketService;

    @Autowired
    public OrdersApiController(OrdersService ordersService, TicketService ticketService) {
        this.ordersService = ordersService;
        this.ticketService = ticketService;
    }

    @RequestMapping(value = "/api/orders/createOrder", method = RequestMethod.POST)
    public ResponseEntity createOrder(
            @RequestParam("giveDate") String giveDate,
            @RequestParam("returnDate") String returnDate,
            @RequestParam("orderBooks") String[] books_id,
            @RequestParam("ticketCode") String ticketCode
    ) {
        try {
            JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            TicketEntity ticketEntity = ticketService.findTicketByCode(ticketCode);
            int[] selectedBooks = Arrays.stream(books_id).mapToInt(Integer::parseInt).toArray();
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(giveDate);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(returnDate);
            String orderCode = ordersService.createOrder(selectedBooks, jwtUser.getId(), startDate, endDate, ticketEntity);
            return new ResponseEntity<>(orderCode, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ModelMap map = new ModelMap();
            map.addAttribute("errorMessage", e.getStackTrace());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/api/orders/getHistory/{pageNumber}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getOrdersByTicket(
            @RequestParam("ticketCode") String ticketCode,
            @PathVariable int pageNumber) {
        PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
        List<BookOrderEntity> ordersList = ordersService.getOrdersByTicket(ticketService.findTicketByCode(ticketCode), pageable).getContent();
        int countPage = ordersService.getOrdersByTicket(ticketService.findTicketByCode(ticketCode), pageable).getTotalPages();
        List<OrderJson> result = new ArrayList<>();
        IntStream.range(0, ordersList.size()).forEach(count -> {
            OrderJson orderJson = new OrderJson();
            result.add(orderJson.convertBooksOrderEntityToOrderJson(ordersList.get(count)));
        });
        ModelMap map = new ModelMap();
        map.addAttribute("ordersList", result);
        map.addAttribute("countPages", countPage);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/orders/findOrder/{pageNumber}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity findOrderByAllCriteria(
            @RequestParam("searchValue") String searchValue,
            @RequestParam("ticketCode") String ticketCode,
            @PathVariable int pageNumber) {
        PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
        Page<BookOrderEntity> page = ordersService.searchOrder(searchValue, ticketCode, pageable);
        List<BookOrderEntity> ordersList = page.getContent();
        int countPage = page.getTotalPages();
        List<OrderJson> result = new ArrayList<>();
        IntStream.range(0, ordersList.size()).forEach(count -> {
            OrderJson orderJson = new OrderJson();
            result.add(orderJson.convertBooksOrderEntityToOrderJson(ordersList.get(count)));
        });
        ModelMap map = new ModelMap();
        map.addAttribute("ordersList", result);
        map.addAttribute("countPages", countPage);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


}
