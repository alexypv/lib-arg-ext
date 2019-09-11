package su.opencode.library.web.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "LIBRARY", schema = "LIBRARY")
public class LibraryEntity extends AuditEntity {

    private String name;

    public LibraryEntity(String name, UserEntity creator) {
        this.name = name;
        this.setAuditParamsForCreation(creator);
    }

    public LibraryEntity(Integer id) {
        super(id);
    }

    public LibraryEntity() {
    }

    @Column(name = "NAME", nullable = true, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LibraryEntity{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", createdBy='" + getCreatorEntity().getId() + '\'' +
                ", createdWhen=" + createdWhen +
                ", updpersistence.xmlatedBy='" + getUpdaterEntity().getId() + '\'' +
                ", updatedWhen=" + updatedWhen +
                '}';
    }
}
