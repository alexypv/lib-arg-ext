package su.opencode.library.web.utils.JsonObject;

import su.opencode.library.web.model.entities.BookEntity;

import java.util.Date;

public class BookJson {

    public BookJson() {
    }

    public BookJson(String isbnNumber, String name, String author, String publishingName, int yearPublishing, String catalogName, int id, String creatorName, Date createdWhen, String updaterName, Date updatedWhen, boolean isAvailable) {
        this.isbnNumber = isbnNumber;
        this.name = name;
        this.author = author;
        this.publishingName = publishingName;
        this.yearPublishing = yearPublishing;
        this.catalogName = catalogName;
        this.id = id;
        this.creatorName = creatorName;
        this.createdWhen = createdWhen;
        this.updaterName = updaterName;
        this.updatedWhen = updatedWhen;
        this.isAvailable = isAvailable;
    }

    private String isbnNumber;
    private String name;
    private String author;
    private String publishingName;
    private int yearPublishing;
    private String catalogName;
    private int id;


    private boolean isAvailable;
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

    public String getIsbnNumber() {
        return isbnNumber;
    }

    public void setIsbnNumber(String isbnNumber) {
        this.isbnNumber = isbnNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishingName() {
        return publishingName;
    }

    public void setPublishingName(String publishingName) {
        this.publishingName = publishingName;
    }

    public Integer getYearPublishing() {
        return yearPublishing;
    }

    public void setYearPublishing(Integer yearPublishing) {
        this.yearPublishing = yearPublishing;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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


    public boolean isAvailable() { return isAvailable; }

    public void setAvailable(boolean available) { isAvailable = available; }

    public BookJson convertBookEntityToBookJson(BookEntity bookEntity) {
        String catalogName;
        if (bookEntity.getCatalogEntity() != null) {
            catalogName = bookEntity.getCatalogEntity().getName();
        } else {
            catalogName = "Без каталога";
        }

        return new BookJson(
                bookEntity.getIsbnNumber(),
                bookEntity.getName(),
                bookEntity.getAuthor(),
                bookEntity.getPublishingName(),
                bookEntity.getYearPublishing(),
                catalogName,
                bookEntity.getId(),
                bookEntity.getCreatorEntity().getUsername(),
                bookEntity.getCreatedWhen(),
                bookEntity.getUpdaterEntity().getUsername(),
                bookEntity.getUpdatedWhen(),
                bookEntity.isAvailable()
        );
    }
}

