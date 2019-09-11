package su.opencode.library.web.service.catalog;

import su.opencode.library.web.model.entities.CatalogEntity;
import su.opencode.library.web.utils.JsonObject.CatalogJson;

import java.util.List;

public interface CatalogService {

    /**
     * Возвращает список всех каталогов в библиотеке.
     *
     * @return Лист каталогов CatalogEntity.
     */
    List<CatalogJson> getCatalogsByLibraryId(int library_id);

    /**
     * @param id id каталога;
     * @return объект класса CatalogEntity.
     */
    CatalogEntity getCatalogById(int id);


    /**
     * Создает новый каталог в библиотеке.
     *
     * @param name       название каталога;
     * @param library_id ID библиотеки.
     */
    void createCatalog(String name, int library_id, int creator_id);

    /**
     * Редактирует информацию о каталоге.
     *
     * @param id      ID каталога;
     * @param newName новое название каталога.
     */
    void editCatalog(int id, String newName, int updater_id);


    /**
     * @param catalogId массив id-ов каталогов, которые будут удалены.
     */
    void deleteCatalogs(int[] catalogId);


}
