package Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServletResponse;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SpringSecurity{
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		 http
		 .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
		        .requestMatchers("/admin/**").hasRole("ADMIN")
	            .requestMatchers("/Journal/**", "/User/**").authenticated()
	            .anyRequest().permitAll()
	        )
	        .exceptionHandling(ex -> ex
	            .authenticationEntryPoint((request, response, authException) -> {
	                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	            })
	            .accessDeniedHandler((request, response, accessDeniedException) -> {
	                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
	            })
	        )
	        .httpBasic(withDefaults());
     return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
		AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		return auth.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
}
