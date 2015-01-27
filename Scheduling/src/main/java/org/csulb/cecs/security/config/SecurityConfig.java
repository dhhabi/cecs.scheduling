package org.csulb.cecs.security.config;

import javax.inject.Inject;

import org.csulb.cecs.Profiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



/**
 * The default Security configuration for shared services.
 * Infrastructure is based on Spring Security for declaration of
 * authentication and authorization strategies.
 * Particularly, rely on a "pluggable" authentication/authorization store.
 *
 * @author 
 *
 */
@SuppressWarnings("unused")
@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Inject
	Environment env;

	@Inject
	ApplicationContext context;
	
	 @Autowired
	 private UserDetailsService userDetailsService;

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		HttpSecurityConfigurer.INSTANCE.configure(env, context, http);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*if (env.acceptsProfiles(Profiles.DEV)) {
			InMemoryAuthenticationBuilder.INSTANCE.build(env, auth);
		} else {
			// FIXME replace with alternate store
			InMemoryAuthenticationBuilder.INSTANCE.build(env, auth);
		}*/
		auth.userDetailsService(userDetailsService)
        .passwordEncoder(new BCryptPasswordEncoder());
	}

}
