package su.opencode.library.web.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "USER_IMAGE", schema = "LIBRARY")
public class UserImageEntity extends AuditEntity {

    private String filename;
    private String filetype;
    private byte[] data;
    private UserEntity userEntity;

    public UserImageEntity() {
    }

    public UserImageEntity(String filename, String filetype, byte[] data, UserEntity userEntity) {
        this.filename = filename;
        this.filetype = filetype;
        this.data = data;
        this.userEntity = userEntity;
        this.setAuditParamsForCreation(userEntity);
    }

    @Column(name = "FILENAME")
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


    @Column(name = "FILETYPE")
    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    @Lob
    @Column(name = "DATA")
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @OneToOne
    @JoinColumn(name = "FK_USER_ID")

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
