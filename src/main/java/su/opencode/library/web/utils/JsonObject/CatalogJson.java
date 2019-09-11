package su.opencode.library.web.utils.JsonObject;

public class CatalogJson {

    public CatalogJson() {
    }

    public CatalogJson(String name, int id) {
        this.name = name;
        this.id = id;
    }

    private String name;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
