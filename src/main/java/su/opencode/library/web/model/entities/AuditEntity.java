package su.opencode.library.web.model.entities;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@MappedSuperclass
public class AuditEntity implements Serializable {
    private static final long serialVersionUID = 2392069675124015962L;

    protected Integer id;
    /**
     * Author of the created entity.
     */
    private UserEntity creatorEntity;
    /**
     * Time when the entity was created.
     */
    protected Date createdWhen;
    /**
     * Author of last update of the entity.
     */
    private UserEntity updaterEntity;
    /**
     * Last time when the entity was updated.
     */
    protected Date updatedWhen;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AuditEntity() {
    }

    public AuditEntity(Integer id) {
        this.id = id;
    }

    /**
     * Set audit's parameters before entity will be created into a database.
     *
     * @param creator creator of the entity.
     */
    public void setAuditParamsForCreation(UserEntity creator) {
        this.creatorEntity = creator;
        this.updaterEntity = creator;
        this.createdWhen = new Date();
        this.updatedWhen = createdWhen;
    }

    /**
     * Set audit's parameters before entity will be updated into a database.
     *
     * @param updater updater of the entity.
     */
    public void setAuditParamsForUpdate(UserEntity updater) {
        this.updaterEntity = updater;
        this.updatedWhen = new Date();
    }

    @Column(name = "CREATED_WHEN")
    public Date getCreatedWhen() {
        return createdWhen;
    }

    public void setCreatedWhen(Date createdWhen) {
        this.createdWhen = createdWhen;
    }

    @Column(name = "UPDATED_WHEN")
    public Date getUpdatedWhen() {
        return updatedWhen;
    }

    public void setUpdatedWhen(Date updatedWhen) {
        this.updatedWhen = updatedWhen;
    }

    @OneToOne
    @JoinColumn(name = "CREATED_BY", nullable = true)
    @ColumnDefault("NULL")
    public UserEntity getCreatorEntity() {
        return creatorEntity;
    }

    public void setCreatorEntity(UserEntity userEntity) {
        this.creatorEntity = userEntity;
    }

    @OneToOne
    @JoinColumn(name = "UPDATED_BY", nullable = true)
    @ColumnDefault("NULL")
    public UserEntity getUpdaterEntity() {
        return updaterEntity;
    }

    public void setUpdaterEntity(UserEntity userEntity) {
        this.updaterEntity = userEntity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditEntity that = (AuditEntity) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}