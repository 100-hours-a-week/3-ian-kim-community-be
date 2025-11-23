package ktb3.full.community.config;

import ktb3.full.community.security.userdetails.AuthUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithAuthMockUserSecurityContextFactory implements WithSecurityContextFactory<WithAuthMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithAuthMockUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        AuthUserDetails principal = new AuthUserDetails(
                annotation.userId(),
                annotation.email(),
                annotation.password(),
                annotation.nickname()
        );

        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        context.setAuthentication(authentication);

        return context;
    }
}
