package com.exiger.pocokta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${security.clientId}")
	private String clientId;

	@Value("${security.clientSecret}")
	private String clientSecret;


	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/admin").access("hasRole('ROLE_USER')").antMatchers("/user").authenticated()
				.and()
					.oauth2Login().clientRegistrationRepository(clientRegistrationRepository());/*.and()
				.logout().deleteCookies().invalidateHttpSession(true).logoutSuccessUrl("/").permitAll();;/*
				.authorizedClientService(authorizedClientService());/*.authorizationEndpoint()
				.authorizationRequestRepository(authorizationRequestRepository()).and()
				.tokenEndpoint()
				.accessTokenResponseClient(accessTokenResponseClient())
				.and()
				.defaultSuccessUrl("/admin");


		/*http.authorizeRequests()
				.antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
				.and().formLogin(); */
	}



	@Bean
	public OAuth2AuthorizedClientService authorizedClientService() {
		return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
	}

	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
		return new HttpSessionOAuth2AuthorizationRequestRepository();
	}

	@Bean
	public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
		DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
		return accessTokenResponseClient;
	}


	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		return new InMemoryClientRegistrationRepository(getOktaRegistration());
	}

	private ClientRegistration getOktaRegistration() {
		return CommonOAuth2Provider.OKTA.getBuilder("Okta")
				.authorizationUri("https://exigertrust.oktapreview.com/oauth2/auskp0sgn4DDDIY1t0h7/v1/authorize")
				.jwkSetUri("https://exigertrust.oktapreview.com/oauth2/auskp0sgn4DDDIY1t0h7/v1/keys")
				.tokenUri("https://exigertrust.oktapreview.com/oauth2/auskp0sgn4DDDIY1t0h7/v1/token").clientId(clientId).clientSecret(clientSecret).build();
	}

}
