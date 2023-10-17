package fr.greta94.cda;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfigNew {

	//@Bean
	protected InMemoryUserDetailsManager configAuthentication() {

		List<UserDetails> users = new ArrayList<>();
		List<GrantedAuthority> adminAuthority = new ArrayList<>();
		adminAuthority.add(new SimpleGrantedAuthority("ADMIN"));
		adminAuthority.add(new SimpleGrantedAuthority("EMPLOYE"));
		UserDetails admin = new User("admin", "{noop}admin", adminAuthority);
		users.add(admin);

		List<GrantedAuthority> employeeAuthority = new ArrayList<>();
		employeeAuthority.add(new SimpleGrantedAuthority("EMPLOYE"));
		UserDetails employee = new User("employe", "{noop}employe", employeeAuthority);
		users.add(employee);

		List<GrantedAuthority> managerAuthority = new ArrayList<>();
		managerAuthority.add(new SimpleGrantedAuthority("MANAGER"));
		UserDetails manager = new User("manager", "{noop}manager", managerAuthority);
		users.add(manager);

		return new InMemoryUserDetailsManager(users);
	}

	//@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// configure les autorisations pour chaque page (url)
		http.csrf().disable().authorizeHttpRequests()
			.antMatchers("/api/bienvenue").permitAll()
				.antMatchers("/api/defaut").permitAll()
				.antMatchers("/api/home").authenticated()
				.antMatchers("/api/admin").hasAuthority("ADMIN")
				.antMatchers("/api/adminbis").hasAuthority("ADMIN")
				.antMatchers("/api/employe").hasAnyAuthority("EMPLOYE", "MANAGER")
				.antMatchers("/api/newemploye").hasAuthority("EMPLOYE")
				.antMatchers("/api/manager").hasAuthority("MANAGER")
				.antMatchers("/api/common").hasAnyAuthority("EMPLOYE", "MANAGER")
				.antMatchers("/api/perso/**").hasAnyAuthority("EMPLOYE", "MANAGER", "ADMIN")
				.antMatchers("/webjars/**").permitAll()
				.and().formLogin().loginPage("/login").permitAll()
				
				.and().exceptionHandling().accessDeniedPage("/error")
				.and().logout().logoutSuccessUrl("/logout.html").permitAll()
				.and().httpBasic();
		return http.build();
	}
}


