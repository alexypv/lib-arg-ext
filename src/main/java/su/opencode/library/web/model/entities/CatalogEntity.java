package su.opencode.library.web.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "CATALOG", schema = "LIBRARY")

public class CatalogEntity extends AuditEntity {

    private String name;
    private LibraryEntity libraryEntity;

    public CatalogEntity(Integer id) {
        super(id);
    }

    public CatalogEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public CatalogEntity() {
    }

    public CatalogEntity(String name, LibraryEntity libraryEntity, UserEntity creator) {
        this.name = name;
        this.libraryEntity = libraryEntity;
        this.setAuditParamsForCreation(creator);
    }

    @Column(name = "NAME", nullable = true, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "FK_LIBRARY_ID")
    public LibraryEntity getLibraryEntity() {
        return libraryEntity;
    }

    public void setLibraryEntity(LibraryEntity libraryEntity) {
        this.libraryEntity = libraryEntity;
    }

    @Override
    public String toString() {
        return "CatalogEntity{" +
                "name='" + name + '\'' +
                ", libraryEntity.id=" + libraryEntity.id +
                ", id=" + id +
                ", createdBy='" + getCreatorEntity().getId() + '\'' +
                ", createdWhen=" + createdWhen +
                ", updatedBy='" + getUpdaterEntity().getId() + '\'' +
                ", updatedWhen=" + updatedWhen +
                '}';
    }
}
