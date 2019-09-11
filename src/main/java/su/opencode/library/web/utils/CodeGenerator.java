package su.opencode.library.web.utils;

import su.opencode.library.web.model.entities.UserEntity;

import java.util.Date;

public class CodeGenerator {

    public String generateTicketNumber(UserEntity userEntity) {
        String code = "RT" + userEntity.getId() + userEntity.getLibraryEntity().getId();
        return code;
    }

    public String generateOrderNumber() {
        String code = "RO" + new Date().hashCode();
        return code;
    }
}
