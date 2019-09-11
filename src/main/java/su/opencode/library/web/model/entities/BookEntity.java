package su.opencode.library.web.model.entities;


import javax.persistence.*;

@Entity
@Table(name = "BOOK", schema = "LIBRARY")
public class BookEntity extends AuditEntity {

    private String isbnNumber;
    private String name;
    private String author;
    private String publishingName;
    private Integer yearPublishing;

    private CatalogEntity catalogEntity;
    private LibraryEntity libraryEntity;
    private boolean isAvailable;

    public BookEntity(String isbnNumber, String name, String author, String publishingName, Integer yearPublishing, LibraryEntity libraryEntity, UserEntity creator, CatalogEntity catalogEntity) {
        this.isbnNumber = isbnNumber;
        this.name = name;
        this.author = author;
        this.publishingName = publishingName;
        this.yearPublishing = yearPublishing;
        this.libraryEntity = libraryEntity;
        this.catalogEntity = catalogEntity;
        this.isAvailable = true;
        this.setAuditParamsForCreation(creator);
    }


    public BookEntity() {
    }

    @Column(name = "ISBN_NUMBER", nullable = false, length = 13)
    public String getIsbnNumber() {
        return isbnNumber;
    }

    public void setIsbnNumber(String isbnNumber) {
        this.isbnNumber = isbnNumber;
    }

    @Column(name = "NAME", nullable = false, length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "AUTHOR", nullable = false, length = 100)
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Column(name = "PUBLISHING_NAME", nullable = false, length = 45)
    public String getPublishingName() {
        return publishingName;
    }

    public void setPublishingName(String publishingName) {
        this.publishingName = publishingName;
    }

    @Column(name = "YEAR_PUBLISHING", nullable = false)
    public Integer getYearPublishing() {
        return yearPublishing;
    }

    public void setYearPublishing(Integer yearPublishing) {
        this.yearPublishing = yearPublishing;
    }

    @Column(name = "IS_AVAILABLE", nullable = false)
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @ManyToOne
    @JoinColumn(name = "FK_CATALOG_ID", nullable = true)
    public CatalogEntity getCatalogEntity() {
        return catalogEntity;
    }

    public void setCatalogEntity(CatalogEntity catalogEntity) {
        this.catalogEntity = catalogEntity;
    }

    @ManyToOne
    @JoinColumn(name = "FK_LIBRARY_ID", nullable = false)
    public LibraryEntity getLibraryEntity() {
        return libraryEntity;
    }

    public void setLibraryEntity(LibraryEntity libraryEntity) {
        this.libraryEntity = libraryEntity;
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "isbnNumber='" + isbnNumber + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", publishingName='" + publishingName + '\'' +
                ", yearPublishing=" + yearPublishing +
                ", catalogEntity=" + catalogEntity +
                ", libraryEntity=" + libraryEntity +
                ", isAvailable=" + isAvailable +
                ", id=" + id +
                ", createdWhen=" + createdWhen +
                ", updatedWhen=" + updatedWhen +
                '}';
    }
}
