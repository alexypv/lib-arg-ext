package su.opencode.library.web.service.library;


import su.opencode.library.web.model.entities.LibraryEntity;
import su.opencode.library.web.model.entities.UserEntity;

import java.util.List;

public interface LibraryService {

    /**
     * @param library_id ID библиотеки
     * @return библиотека LibraryEntity.
     */
    LibraryEntity getLibraryById(int library_id);

    /**
     * Создает библиотеку.
     *
     * @param name       название библиотеки.
     * @param creator_id Имя создателя.
     */
    void createLibrary(String name, UserEntity creator);

    /**
     * @return List<Librarys> список всех библиотек в системе
     * Используется только у глобального админа при перемещении пользователя из одной библиотеки в другую
     */
    List<LibraryEntity> getAllLibrary();

    /**
     * Изменить информацию о библиотеке.
     *
     * @param id      ID библиотеки;
     * @param newName новое название библиотеки.
     */
    void editLibrary(int id, String newName);

    /**
     * Удалить библиотеку(и).
     *
     * @param id массив id-ов библиотек.
     */
    void removeLibrary(int[] id);
}
