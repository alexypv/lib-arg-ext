package su.opencode.library.web.model.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ROLES", schema = "LIBRARY")
public class RoleEntity extends AuditEntity {

    public RoleEntity() {
    }

    public RoleEntity(String name) {
        this.name = name;
    }

    private String name;

    @Column(name = "NAME", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<UserEntity> users;

}
