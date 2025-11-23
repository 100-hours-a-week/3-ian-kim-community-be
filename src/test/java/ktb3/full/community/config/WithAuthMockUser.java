package ktb3.full.community.config;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@WithSecurityContext(factory = WithAuthMockUserSecurityContextFactory.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface WithAuthMockUser {

    long userId() default 1L;
    String email() default "email@example.com";
    String password() default "Password123!";
    String nickname() default "name";
}
