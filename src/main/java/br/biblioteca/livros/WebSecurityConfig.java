package br.biblioteca.livros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// HABILITA O FRAME DO H2-CONSOLE
		http.headers().frameOptions().disable();

		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET, "/user/registration").permitAll()

				.antMatchers(HttpMethod.GET, "/livros/list").permitAll()

				.antMatchers(HttpMethod.GET, "/livro/novo").hasRole("ADMIN")

				.antMatchers(HttpMethod.POST, "/livro/gravar").hasRole("ADMIN")

				.antMatchers(HttpMethod.GET, "/livros/editar").hasRole("ADMIN")

				.antMatchers(HttpMethod.GET, "/livros/excluir").hasRole("ADMIN")

				.antMatchers(HttpMethod.GET, "/users/novo").hasRole("ADMIN")

				.antMatchers(HttpMethod.GET, "/users/editar").hasRole("ADMIN")

				.antMatchers(HttpMethod.GET, "/users/list").hasRole("ADMIN")

				.and().formLogin().loginPage("/users/login").permitAll().and().logout().permitAll();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());

	}

}