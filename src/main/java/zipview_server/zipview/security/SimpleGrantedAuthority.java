package zipview_server.zipview.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

public final class SimpleGrantedAuthority implements GrantedAuthority {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    public SimpleGrantedAuthority(String s) {
    }

    @Override
    public String getAuthority() {
        return null;
    }
}