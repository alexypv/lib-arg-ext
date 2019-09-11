package su.opencode.library.web.secure;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import su.opencode.library.web.model.entities.RoleEntity;
import su.opencode.library.web.model.entities.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(UserEntity userEntity) {

        return new JwtUser(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getSurname(),
                userEntity.getName(),
                userEntity.getSecondName(),
                userEntity.getPassword(),
                userEntity.getLibraryEntity().getId(),
                mapToGrantedAuthorities(new ArrayList<>(userEntity.getRoles()))

        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<RoleEntity> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
