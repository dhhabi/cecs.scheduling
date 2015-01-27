package org.csulb.cecs.security.config;

import org.csulb.cecs.security.dto.FunctionalRole;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;

/**
 * 
 * @author
 *
 */
enum InMemoryAuthenticationBuilder {

    INSTANCE;

    void build(Environment env, AuthenticationManagerBuilder auth) throws Exception {
        String username = env.getProperty("app.user.name", "admin");
        String password = env.getProperty("app.user.password", "admin");
        InMemoryUserDetailsManagerConfigurer<?> inmem = auth.inMemoryAuthentication();

        // remember to annotate service methods with @org.springframework.security.access.annotation.Secured
        inmem.withUser(username).password(password).authorities(FunctionalRole.ADMIN.getRoleName());

        // other "fake" accounts; for demonstration purposes
        inmem.withUser("user").password("user").authorities(FunctionalRole.PUBLIC.getRoleName());

    }
}
