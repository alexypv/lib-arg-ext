package su.opencode.library.web.utils;

import su.opencode.library.web.model.entities.UserEntity;

import java.util.Date;

public class CodeGenerator {

    public String generateTicketNumber(UserEntity userEntity) {
        return "RT" + userEntity.getId() + userEntity.getLibraryEntity().getId();
    }

    public String generateOrderNumber() {
        return "RO" + new Date().hashCode();
    }
}
