package su.opencode.library.web.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "TICKET", schema = "LIBRARY")
public class TicketEntity extends AuditEntity {

    private String code;
    private UserEntity userEntity;

    public TicketEntity() {
    }

    public TicketEntity(String code, UserEntity userEntity, UserEntity creator) {
        this.code = code;
        this.userEntity = userEntity;
        this.setAuditParamsForCreation(creator);
    }

    @Column(name = "CODE", nullable = false, unique = true)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @ManyToOne
    @JoinColumn(name = "FK_USER_ID")
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public String toString() {
        return "TicketEntity{" +
                "code=" + code +
                ", readerEntity.id=" + userEntity.id +
                ", id=" + id +
                ", createdBy='" + getCreatorEntity().getId() + '\'' +
                ", createdWhen=" + createdWhen +
                ", updatedBy='" + getUpdaterEntity().getId() + '\'' +
                ", updatedWhen=" + updatedWhen +
                '}';
    }
}