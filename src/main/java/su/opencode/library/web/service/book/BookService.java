package su.opencode.library.web.service.book;

import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;
import su.opencode.library.web.model.entities.BookEntity;
import su.opencode.library.web.utils.JsonObject.BookJson;

import java.util.List;

public interface BookService {

    BookJson getBookById(int book_id);

    /**
     * Выгружает список книг в выбранном каталоге.
     *
     * @param catalog_id ID каталога
     * @return Список List книг в каталоге.
     */
    ModelMap getPageBooksInCatalog(String catalog_id, int library_id, Pageable pageable);

    List<BookEntity> getListBooksInCatalog(int catalog_id);

    /**
     * Создает книгу в библиотеке. Изначально не привязана ни к какому каталогу.
     * fk_catalog = NULL.
     *
     * @param isbnNumber     ISBN - номер книги. 13-значное число;
     * @param name           название книги;
     * @param author         автор;
     * @param publishingName название издательства;
     * @param yearPublishing год издания.
     * @param library_id     ID библиотеки, в которой создается книга.
     */
    void createBook(String isbnNumber, String name, String author, String publishingName, int yearPublishing, int library_id, int creator_id, String catalog_id);

    /**
     * Изменить информацию о книге.
     * Не перемещает книгу в другой каталог, поле fk_catalog не меняется.
     *
     * @param id             ID изменяемой книги;
     * @param isbnNumber     новый ISBN - номер книги. 13-значное число;
     * @param name           новое название книги;
     * @param author         новый автор;
     * @param publishingName новое название издательства;
     * @param yearPublishing новый год издания.
     */
    void editBook(int id, String isbnNumber, String name, String author, String publishingName, String yearPublishing, int updater_id);

    /**
     * Удаляет книги из системы.
     *
     * @param book_ids массив ID-ов книг.
     */
    void deleteBook(int[] book_ids);

    /**
     * Добавляет в каталог книгу по параметрам ID и fk_catalog = NULL.
     * fk_catalog = NULL гарантирует, что книга на момент добавления не привязана ни к какому другому каталогу.
     * Подзапрос b.fkLibrary = (select c.fkLibrary from CatalogEntity c where c.id =: catalog_id  гарантирует,
     * что в каталог будет добавлена книга только из этой же библиотеки.
     *
     * @param catalog_id id каталога;
     * @param books_id   массив id-ов книг.
     */
    void addInCatalog(int catalog_id, int[] books_id);


    /**
     * Удаление книги из каталога.
     * При удалении книги из каталога ей будет проставлен fk_catalog = NULL.
     *
     * @param catalog_id ID каталога, из которого удаляются книги;
     * @param books_id   массив id-ов книг, которые удаляются из каталога.
     */
    void removeFromCatalog(int[] books_id);

    void moveBook(int[] books_id, int newCatalogId, int updater_id);

    List<BookJson> getBooksById(int[] id);

}
