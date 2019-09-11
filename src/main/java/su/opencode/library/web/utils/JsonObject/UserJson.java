package su.opencode.library.web.utils.JsonObject;
import su.opencode.library.web.model.entities.UserEntity;
import su.opencode.library.web.service.ticket.TicketServiceImpl;

import java.util.Date;

public class UserJson {



    public UserJson() {
    }

    public UserJson(int id, String username, String surname, String name,
                    String secondName, String creatorName,
                    Date createdWhen, String updaterName, Date updatedWhen,
                    String roleName, String libraryName) {
        this.id = id;
        this.username = username;
        this.surname = surname;
        this.name = name;
        this.secondName = secondName;
        this.creatorName = creatorName;
        this.createdWhen = createdWhen;
        this.updaterName = updaterName;
        this.updatedWhen = updatedWhen;
        this.roleName = roleName;
        this.libraryName = libraryName;
    }

    private int id;
    private String username;
    private String surname;
    private String name;
    private String secondName;
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
    private String roleName;
    private String libraryName;
    private String ticketCode;

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreatedWhen() {
        return createdWhen;
    }

    public void setCreatedWhen(Date createdWhen) {
        this.createdWhen = createdWhen;
    }

    public String getUpdaterName() {
        return updaterName;
    }

    public void setUpdaterName(String updaterName) {
        this.updaterName = updaterName;
    }

    public Date getUpdatedWhen() {
        return updatedWhen;
    }

    public void setUpdatedWhen(Date updatedWhen) {
        this.updatedWhen = updatedWhen;
    }


    public UserJson convertUserEntityToUserJson(UserEntity userEntity) {

        return new UserJson(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getSurname(),
                userEntity.getName(),
                userEntity.getSecondName(),
                userEntity.getCreatorEntity().getUsername(),
                userEntity.getCreatedWhen(),
                userEntity.getUpdaterEntity().getUsername(),
                userEntity.getUpdatedWhen(),
                userEntity.getRoles().get(0).getName(),
                userEntity.getLibraryEntity().getName()
        );
    }

}
