package com.iridiumit.controleos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.iridiumit.controleos.security.OSUserDetailsService;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	final OSUserDetailsService userDetailsService;

	public WebSecurityConfig(OSUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			authorizeRequests()
				.antMatchers("/").hasAnyRole("OS_ADMIN", "OS_ATENDIMENTO", "OS_TECNICO", "OS_ORCAMENTO")
				.antMatchers("/administracao/**").hasAnyRole("OS_ADMIN")
				.antMatchers("/relatorios/**").hasAnyRole("OS_ADMIN")
				.antMatchers("/atendimento/**").hasAnyRole("OS_ADMIN","OS_ATENDIMENTO")
				.antMatchers("/tecnico/**").hasAnyRole("OS_ADMIN","OS_TECNICO")
				.antMatchers("/orcamento/**").hasAnyRole("OS_ADMIN","OS_ORCAMENTO","OS_TECNICO")
				.anyRequest()
				.authenticated()
				.antMatchers("/resources/**", "/signup", "/about", "/imagens_produtos/**").permitAll()
			.and()
			.formLogin()
				.loginPage("/entrar")
				.permitAll()
			.and()
			.logout()
				.logoutSuccessUrl("/entrar?logout")
				.permitAll()
			.and()
			.rememberMe()
				.userDetailsService(userDetailsService)
			.and()
			.exceptionHandling().accessDeniedPage("/acessonegado");
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/resources/**");
	}
}
