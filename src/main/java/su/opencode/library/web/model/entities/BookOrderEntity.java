package su.opencode.library.web.model.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BOOKSORDER", schema = "LIBRARY")
public class BookOrderEntity extends AuditEntity {

    private String code;
    private Date startDate;
    private Date endDate;
    private Date realReturnDate;
    private TicketEntity ticketEntity;
    private LibraryEntity libraryEntity;

    public BookOrderEntity() {
    }

    public BookOrderEntity(String code, Date startDate, Date endDate, TicketEntity ticketEntity, UserEntity creator, LibraryEntity libraryEntity) {
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ticketEntity = ticketEntity;
        this.libraryEntity = libraryEntity;
        this.setAuditParamsForCreation(creator);
    }

    @Column(name = "CODE", nullable = false)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "START_DATE", columnDefinition = "DATE", nullable = true)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "REAL_RETURN_DATE", columnDefinition = "DATE", nullable = true)
    public Date getRealReturnDate() {
        return realReturnDate;
    }

    public void setRealReturnDate(Date realReturnDate) {
        this.realReturnDate = realReturnDate;
    }

    @Column(name = "END_DATE", columnDefinition = "DATE", nullable = true)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @ManyToOne
    @JoinColumn(name = "FK_TICKET_ID")
    public TicketEntity getTicketEntity() {
        return ticketEntity;
    }

    public void setTicketEntity(TicketEntity ticketEntity) {
        this.ticketEntity = ticketEntity;
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
        return "OrderEntity{" +
                "code='" + code + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", realReturnDate=" + realReturnDate +
                ", ticketEntity.id=" + ticketEntity.id +
                ", id=" + id +
                ", createdBy='" + getCreatorEntity().getId() + '\'' +
                ", createdWhen=" + createdWhen +
                ", updatedBy='" + getUpdaterEntity().getId() + '\'' +
                ", updatedWhen=" + updatedWhen +
                '}';
    }
}
