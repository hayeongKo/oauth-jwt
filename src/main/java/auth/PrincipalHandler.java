package auth;

import org.springframework.security.core.context.SecurityContextHolder;

public class PrincipalHandler {
    public static Long getMemberIdFromPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Long.valueOf(principal.toString());
    }
}
