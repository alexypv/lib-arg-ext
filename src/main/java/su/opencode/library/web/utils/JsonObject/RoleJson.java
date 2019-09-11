package su.opencode.library.web.utils.JsonObject;

import su.opencode.library.web.model.entities.RoleEntity;

import java.util.Date;

public class RoleJson {

    public RoleJson() {
    }

    public RoleJson(int id, String roleName) {
        this.id = id;
        this.name = roleName;
    }


    private int id;

    private String name;

    private String creatorName;
    /**
     * Time when the entity was created.
     */
    private Date createdWhen;
    /**
     * Author of last update of the entity.
     */
    private String updaterName;
    /**
     * Last time when the entity was updated.
     */
    private Date updatedWhen;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String roleName) {
        this.name = roleName;
    }


    public RoleJson convertRoleEntityToRoleJson(RoleEntity roleEntity) {

        return new RoleJson(
                roleEntity.getId(),
                roleEntity.getName()

        );
    }

}
