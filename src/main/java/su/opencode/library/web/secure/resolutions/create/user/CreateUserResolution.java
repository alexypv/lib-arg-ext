package su.opencode.library.web.secure.resolutions.create.user;

import java.util.ArrayList;
import java.util.List;

public enum CreateUserResolution {

    ROLE_GLOBAL,
    ROLE_ADMIN,
    ROLE_LIBRARIER,
    ROLE_READER;

    private static final List<CreateUserResolution> globalRes = new ArrayList<CreateUserResolution>() {{
        add(ROLE_ADMIN);
        add(ROLE_LIBRARIER);
        add(ROLE_READER);
    }};

    private static final List<CreateUserResolution> adminRes = new ArrayList<CreateUserResolution>() {{
        add(ROLE_LIBRARIER);
        add(ROLE_READER);
    }};


    private static final List<CreateUserResolution> libRes = new ArrayList<CreateUserResolution>() {{
        add(ROLE_READER);
    }};

    public static List<CreateUserResolution> getGlobalRes() {
        return globalRes;
    }

    public static List<CreateUserResolution> getAdminRes() {
        return adminRes;
    }

    public static List<CreateUserResolution> getLibRes() {
        return libRes;
    }
}
