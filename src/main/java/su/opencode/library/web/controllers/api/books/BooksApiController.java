package su.opencode.library.web.controllers.api.books;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import su.opencode.library.web.controllers.BaseController;
import su.opencode.library.web.model.entities.BookEntity;
import su.opencode.library.web.model.entities.RoleEntity;
import su.opencode.library.web.secure.JwtUser;
import su.opencode.library.web.service.book.BookService;
import su.opencode.library.web.service.catalog.CatalogService;
import su.opencode.library.web.service.library.LibraryService;
import su.opencode.library.web.service.order.OrdersService;
import su.opencode.library.web.service.roles.RolesService;
import su.opencode.library.web.service.ticket.TicketService;
import su.opencode.library.web.service.user.UserService;
import su.opencode.library.web.utils.JsonObject.BookJson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@RestController
public class BooksApiController extends BaseController {


    public BooksApiController(BookService bookService, RolesService rolesService, UserService userService, CatalogService catalogService, OrdersService ordersService, TicketService ticketService, LibraryService libraryService) {
        super(bookService, rolesService, userService, catalogService, ordersService, ticketService, libraryService);
    }

    @RequestMapping(value = "/api/books/openCatalog/{id}/{pageNumber}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getBooksInCatalog(@PathVariable String id,
                                            @PathVariable int pageNumber) {

        jwtUser.setCurrentPage(pageNumber);
        jwtUser.setCurrentSection(id);
        return getCurrentPage(id, pageNumber);
    }

    @RequestMapping(value = "/api/books/openBook/{id}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity openBook(@PathVariable String id) {
        if (!id.isEmpty()) {

            BookJson result = bookService.getBookById(Integer.valueOf(id));
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        // если пришла пустая строка
        return new ResponseEntity<>(Collections.EMPTY_LIST, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @RequestMapping(value = "/api/books/createBook", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity createBook(
            @RequestParam("newISBNBookNumber") String bookNumber,
            @RequestParam("newAuthorLastName") String lastName,
            @RequestParam("newAuthorFirstName") String firstName,
            @RequestParam("newAuthorSecondName") String secondName,
            @RequestParam("newBookName") String bookName,
            @RequestParam("newPublishingName") String publishingName,
            @RequestParam("newYearPublishing") int yearPublishing,
            @RequestParam("countCreate") int countCreate,
            @RequestParam("catalogsChoosenList") String catalog_id
    ) {

        String author = lastName + " " + firstName + " " + secondName;
        for (int count = 0; count < countCreate; count++) {
            bookService.createBook(bookNumber, bookName, author, publishingName, yearPublishing, jwtUser.getLibrary_id(), jwtUser.getId(), catalog_id);
        }
        return getCurrentPage(jwtUser.getCurrentSection(), jwtUser.getCurrentPage());
    }


    @RequestMapping(value = "/api/books/editBook", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity editBook(
            @RequestParam("newISBNBookNumber") String bookNumber,
            @RequestParam("newAuthorName") String author,
            @RequestParam("newBookName") String bookName,
            @RequestParam("newPublishingName") String publishingName,
            @RequestParam("newYearPublishing") String yearPublishing,
            @RequestParam("book_id") String bookId
    ) {

        bookService.editBook(Integer.valueOf(bookId), bookNumber, bookName, author, publishingName, yearPublishing, jwtUser.getId());
        return getCurrentPage(jwtUser.getCurrentSection(), jwtUser.getCurrentPage());
    }

    @RequestMapping(value = "/api/books/deleteBook", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity deleteBook(
            @RequestParam("choosenBooksForDelete") String[] books_id
    ) {

        if (books_id != null && books_id.length > 0) {

            int[] removableId = Arrays.stream(books_id).mapToInt(Integer::parseInt).toArray();
            bookService.deleteBook(removableId);
            return getCurrentPage(jwtUser.getCurrentSection(), jwtUser.getCurrentPage());
        } else {
            throw new NullPointerException("Параметр books_id не предоставлен!");
        }
    }

    @RequestMapping(value = "/api/books/deleteBookFromCatalog", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity removeBookFromCatalog(
            @RequestParam("choosenBooksForDeleteInCatalog") String[] books_id
    ) {
        if (books_id != null && books_id.length > 0) {

            int[] removableId = Arrays.stream(books_id).mapToInt(Integer::parseInt).toArray();
            bookService.removeFromCatalog(removableId);
            return getCurrentPage(jwtUser.getCurrentSection(), jwtUser.getCurrentPage());
        } else {
            throw new NullPointerException("Параметр books_id не предоставлен!");
        }
    }

    @RequestMapping(value = "/api/books/moveBook", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity moveBooks(
            @RequestParam("choosenBooks") String[] books_id,
            @RequestParam("catalogsChoosenList") int catalog_id
    ) {

        int[] moveBooks = Arrays.stream(books_id).mapToInt(Integer::parseInt).toArray();
        bookService.moveBook(moveBooks, catalog_id, jwtUser.getId());
        return getCurrentPage(jwtUser.getCurrentSection(), jwtUser.getCurrentPage());
    }


    @RequestMapping(value = "/api/books/getBooksForCreateOrder", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getBooksForCreateOrder(
            @RequestParam("selectedBooks") String[] books_id
    ) {

        int[] selectedBooks = Arrays.stream(books_id).mapToInt(Integer::parseInt).toArray();
        ModelMap map = new ModelMap();
        PageRequest pageable = PageRequest.of(0, 10);
        map.addAttribute("booksList", bookService.getBooksById(selectedBooks));
        RoleEntity roleEntity = rolesService.getRoleByName("ROLE_READER");
        map.addAttribute("readersList", userService.getUsersByRolesAndLibrary(roleEntity.getId(), pageable, jwtUser.getLibrary_id()).get("usersList"));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    private ResponseEntity getCurrentPage(String id, int pageNumber) {
        try {
            PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
            return new ResponseEntity<>(bookService.getPageBooksInCatalog(id, jwtUser.getLibrary_id(), pageable), HttpStatus.OK);
        } catch (NumberFormatException e) {
            ModelMap map = new ModelMap();
            map.addAttribute("Error message", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/api/books/getBooksInOrder/{pageNumber}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getBooksInOrder(@PathVariable int pageNumber,
                                          @RequestParam("orderID") int orderID) {
        PageRequest pageable = PageRequest.of(pageNumber - 1, 10);
        Page<BookEntity> pageBooks = bookService.getBooksInOrder(orderID, pageable);
        List<BookEntity> booksList = pageBooks.getContent();
        int countPage = pageBooks.getTotalPages();
        List<BookJson> result = new ArrayList<>();

        IntStream.range(0, booksList.size()).forEach(count -> {
            BookJson bookJson = new BookJson();
            result.add(bookJson.convertBookEntityToBookJson(booksList.get(count)));
        });
        ModelMap map = new ModelMap();
        map.addAttribute("booksList", result);
        map.addAttribute("countPages", countPage);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
