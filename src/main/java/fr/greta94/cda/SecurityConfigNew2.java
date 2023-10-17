package fr.greta94.cda;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfigNew2 {

	@Bean
	protected InMemoryUserDetailsManager configAuthentication() {
		UserDetails admin =
				 User.builder()
					.username("admin")
					.password("{noop}admin")
					.roles("ADMIN","EMPLOYE","MANAGER")
					.build();
		
		UserDetails employe =
				 User.builder()
					.username("employe")
					.password("{noop}employe")
					.roles("EMPLOYE")
					.build();
		
		UserDetails manager =
				 User.builder()
					.username("manager")
					.password("{noop}manager")
					.roles("MANAGER","EMPLOYE")
					.build();
		return new InMemoryUserDetailsManager(admin,manager,employe);
			}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// configure les autorisations pour chaque page (url)
		http.csrf().disable().authorizeHttpRequests(request ->request
			.antMatchers("/api/bienvenue").permitAll()
				.antMatchers("/api/defaut").permitAll()
				.antMatchers("/api/home").authenticated()
				.antMatchers("/api/admin").hasRole("ADMIN")
				.antMatchers("/api/adminbis").hasRole("ADMIN")
				.antMatchers("/api/employe").hasAnyRole("EMPLOYE", "MANAGER")
				.antMatchers("/api/newemploye").hasRole("EMPLOYE")
				.antMatchers("/api/manager").hasRole("MANAGER")
				.antMatchers("/api/common").hasAnyRole("EMPLOYE", "MANAGER")
				.antMatchers("/api/perso/**").hasAnyRole("EMPLOYE", "MANAGER", "ADMIN")
				.antMatchers("/webjars/**").permitAll())
				.formLogin().loginPage("/login").permitAll()
	
				.and().exceptionHandling().accessDeniedPage("/error")
				.and().logout().logoutSuccessUrl("/logout.html").permitAll()
				.and().httpBasic();
		return http.build();
	}
}


