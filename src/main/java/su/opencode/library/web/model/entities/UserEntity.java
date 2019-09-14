package su.opencode.library.web.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity

@Table(name = "USER", schema = "LIBRARY")
public class UserEntity extends AuditEntity {

    private String username;
    private String password;
    private String surname;
    private String name;
    private String secondName;

    public UserEntity() {
    }

    public UserEntity(Integer id) {
        super(id);
    }

    public UserEntity(String username, String password, String surname, String name, String secondName, LibraryEntity libraryEntity, List<RoleEntity> roles, UserEntity creator) {
        this.username = username;
        this.password = password;
        this.surname = surname;
        this.name = name;
        this.secondName = secondName;
        this.libraryEntity = libraryEntity;
        this.roles = roles;
        this.setAuditParamsForCreation(creator);
    }

    private LibraryEntity libraryEntity;


    @Column(name = "USERNAME", nullable = false, length = 45, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Column(name = "PASSWORD", nullable = false, length = 1000)
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Column(name = "SURNAME", length = 45)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    @Column(name = "NAME", length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Column(name = "SECOND_NAME", length = 45)
    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }


    @ManyToMany(fetch = FetchType.EAGER)
    @Access(AccessType.FIELD)
    @JoinTable(name = "USER_ROLES", schema = "LIBRARY", joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")}, inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private List<RoleEntity> roles;

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_LIBRARY_ID")
    public LibraryEntity getLibraryEntity() {
        return libraryEntity;
    }

    public void setLibraryEntity(LibraryEntity libraryEntity) {
        this.libraryEntity = libraryEntity;
    }

}

