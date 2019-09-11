package su.opencode.library.web.service.book;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import su.opencode.library.web.model.entities.BookEntity;
import su.opencode.library.web.model.entities.CatalogEntity;
import su.opencode.library.web.model.entities.LibraryEntity;
import su.opencode.library.web.model.entities.UserEntity;
import su.opencode.library.web.repositories.BookCrudRepository;
import su.opencode.library.web.repositories.LibraryCrudRepository;
import su.opencode.library.web.utils.JsonObject.BookJson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BookServiceImpl implements BookService {

    private final BookCrudRepository bookRepository;
    private final LibraryCrudRepository libraryRepository;

    @Autowired
    public BookServiceImpl(BookCrudRepository bookRepository, LibraryCrudRepository libraryRepository) {
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
    }

    @Override
    public BookJson getBookById(int id) {
        BookJson bookJson = new BookJson();
        return bookJson.convertBookEntityToBookJson(Objects.requireNonNull(bookRepository.findById(id).orElse(null)));
    }


    @Override
    public List<BookJson> getBooksById(int[] id) {

        List<BookJson> books = new ArrayList<>();
        for (int count = 0; count < id.length; count++) {
            books.add(getBookById(id[count]));
        }
        return books;
    }

    /**
     * Выгружает список книг в выбранном каталоге.
     *
     * @param catalog_id ID каталога
     * @return Список List книг в каталоге.
     */
    @Override
    public ModelMap getPageBooksInCatalog(String catalog_id, int library_id, Pageable pageable) {
        if (!catalog_id.isEmpty()) {
            List<BookJson> result = new ArrayList<>();

            if (catalog_id.equals("allBooks")) {
                List<BookEntity> booksList = bookRepository.findPageBookEntityByLibraryEntity(libraryRepository.findById(library_id).orElse(null), pageable).getContent();
                for (int count = 0; count < booksList.size(); count++) {
                    BookJson bookJson = new BookJson();
                    result.add(bookJson.convertBookEntityToBookJson(booksList.get(count)));
                }
                ModelMap map = new ModelMap();
                map.addAttribute("booksList", result);
                map.addAttribute("countPage", bookRepository.findPageBookEntityByLibraryEntity(libraryRepository.findById(library_id).orElse(null), pageable).getTotalPages());
                return map;
            } else if (catalog_id.equals("withoutCatalog")) {
                List<BookEntity> booksList = bookRepository.findPageBookEntityByLibraryEntityAndCatalogEntity(new LibraryEntity(library_id), null, pageable).getContent();
                for (int count = 0; count < booksList.size(); count++) {
                    BookJson bookJson = new BookJson();
                    result.add(bookJson.convertBookEntityToBookJson(booksList.get(count)));
                }
                ModelMap map = new ModelMap();
                map.addAttribute("booksList", result);
                map.addAttribute("countPage", bookRepository.findPageBookEntityByLibraryEntityAndCatalogEntity(new LibraryEntity(library_id), null, pageable).getTotalPages());
                return map;

            } else {
                List<BookEntity> booksList = bookRepository.findPageBookEntityByCatalogEntity(new CatalogEntity(Integer.valueOf(catalog_id)), pageable).getContent();
                for (int count = 0; count < booksList.size(); count++) {
                    BookJson bookJson = new BookJson();
                    result.add(bookJson.convertBookEntityToBookJson(booksList.get(count)));
                }
                ModelMap map = new ModelMap();
                map.addAttribute("booksList", result);
                map.addAttribute("countPage", bookRepository.findPageBookEntityByCatalogEntity(new CatalogEntity(Integer.valueOf(catalog_id)), pageable).getTotalPages());
                return map;
            }
        } else {
            ModelMap map = new ModelMap();
            map.addAttribute("error", "Каталог не найден!");
            return map;
        }
    }

    @Override
    public List<BookEntity> getListBooksInCatalog(int catalog_id) {
        return bookRepository.findListBookEntityByCatalogEntity(new CatalogEntity(catalog_id));
    }

    @Override
    public void createBook(String isbnNumber, String name, String author, String publishingName, int yearPublishing, int library_id, int creator_id, String catalog_id) {
        if (!catalog_id.equals("withoutCatalog")) {
            bookRepository.save(new BookEntity(isbnNumber, name, author, publishingName, yearPublishing,
                    new LibraryEntity(library_id), new UserEntity(creator_id), new CatalogEntity(Integer.valueOf(catalog_id))));
        } else {
            bookRepository.save(new BookEntity(isbnNumber, name, author, publishingName, yearPublishing,
                    new LibraryEntity(library_id), new UserEntity(creator_id), null));
        }
    }


    @Override
    public void editBook(int id, String isbnNumber, String name, String author, String publishingName, String yearPublishing, int updater_id) {
        // Если хотя бы что-то не пустое, то обратиться к базе, а дальше проверяю конкретно каждый параметр

        BookEntity bookEntity = bookRepository.findById(id).get();
        UserEntity updater = new UserEntity(updater_id);

        if (!isbnNumber.isEmpty()) {
            bookEntity.setIsbnNumber(isbnNumber);
        }

        if (!name.isEmpty()) {
            bookEntity.setName(name);
        }

        if (!author.isEmpty()) {
            bookEntity.setAuthor(author);
        }

        if (!publishingName.isEmpty()) {
            bookEntity.setPublishingName(publishingName);
        }

        if (!yearPublishing.isEmpty()) {
            bookEntity.setYearPublishing(Integer.valueOf(yearPublishing));
        }
        bookEntity.setAuditParamsForUpdate(updater);
        bookRepository.save(bookEntity);
    }


    @Override
    public void deleteBook(int[] book_ids) {
        for (int id : book_ids) {
            //Добавить проверку на наличие в заказе, если есть в заказе, то запретить удаление
            bookRepository.deleteById(id);
        }
    }


    @Override
    public void addInCatalog(int catalog_id, int[] books_id) {
        CatalogEntity catalog = new CatalogEntity(catalog_id);
        for (int id : books_id) {
            //Проверяю, если книга привязана к той же библиотеке, что и каталог, то перемещаю
            if (bookRepository.findById(id).get().getLibraryEntity().getId().equals(catalog.getLibraryEntity().getId())) {
                bookRepository.findById(id).get().setCatalogEntity(catalog);
            }
        }
    }

    @Override
    public void removeFromCatalog(int[] books_id) {
        for (int id : books_id) {
            BookEntity entity = bookRepository.findById(id).get();
            entity.setCatalogEntity(null);
            bookRepository.save(entity);
        }

    }

    @Override
    public void moveBook(int[] books_id, int newCatalogId, int updater_id) {
        CatalogEntity catalogEntity = new CatalogEntity(newCatalogId);

        for (int id : books_id) {
            BookEntity entity = bookRepository.findById(id).get();
            entity.setCatalogEntity(catalogEntity);
            bookRepository.save(entity);
        }

    }


}
