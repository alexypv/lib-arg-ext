package su.opencode.library.web.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_POSITION", schema = "LIBRARY")
public class OrderPositionEntity extends AuditEntity {

    private BookOrderEntity bookOrderEntity;
    private BookEntity bookEntity;

    public OrderPositionEntity() {
    }

    public OrderPositionEntity(BookOrderEntity bookOrderEntity, BookEntity bookEntity, UserEntity creator) {
        this.bookOrderEntity = bookOrderEntity;
        this.bookEntity = bookEntity;
        this.setAuditParamsForCreation(creator);
    }

    @OneToOne
    @JoinColumn(name = "FK_BOOK_ID")
    public BookEntity getBookEntity() {
        return bookEntity;
    }

    public void setBookEntity(BookEntity bookEntity) {
        this.bookEntity = bookEntity;
    }

    @ManyToOne
    @JoinColumn(name = "FK_ORDER_ID")
    public BookOrderEntity getBookOrderEntity() {
        return bookOrderEntity;
    }

    public void setBookOrderEntity(BookOrderEntity bookOrderEntity) {
        this.bookOrderEntity = bookOrderEntity;
    }

    @Override
    public String toString() {
        return "OrderPositionEntity{" +
                "bookOrderEntity.id=" + bookOrderEntity.id +
                ", bookEntity.id=" + bookEntity.id +
                ", id=" + id +
                ", createdBy='" + getCreatorEntity().getId() + '\'' +
                ", createdWhen=" + createdWhen +
                ", updatedBy='" + getUpdaterEntity().getId() + '\'' +
                ", updatedWhen=" + updatedWhen +
                '}';
    }
}
