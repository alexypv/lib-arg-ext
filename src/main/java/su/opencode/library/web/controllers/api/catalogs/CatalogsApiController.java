package su.opencode.library.web.controllers.api.catalogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import su.opencode.library.web.secure.JwtUser;
import su.opencode.library.web.service.catalog.CatalogService;

import java.util.Arrays;
import java.util.List;

@RestController
public class CatalogsApiController {

    private final CatalogService catalogService;

    @Autowired
    public CatalogsApiController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @RequestMapping(value = "/api/catalogs/loadCatalogs", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<List> getCatalogs() {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return new ResponseEntity<>(catalogService.getCatalogsByLibraryId(jwtUser.getLibrary_id()), HttpStatus.OK);
    }


    @RequestMapping(value = "/api/catalogs/createCatalog", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<List> createCatalog(@RequestParam("newCatalogName") String newCatalogName) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        catalogService.createCatalog(newCatalogName, jwtUser.getLibrary_id(), jwtUser.getId());
        return new ResponseEntity<>(catalogService.getCatalogsByLibraryId(jwtUser.getLibrary_id()), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/catalogs/deleteCatalog", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<List> deleteCatalog(@RequestParam("catalogsChoosenList") String[] catalogs_id) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        int[] removableId = Arrays.stream(catalogs_id).mapToInt(Integer::parseInt).toArray();
        catalogService.deleteCatalogs(removableId);
        return new ResponseEntity<>(catalogService.getCatalogsByLibraryId(jwtUser.getLibrary_id()), HttpStatus.OK);
    }


}
