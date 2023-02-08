package in.learnjavaskills.springsecurity.security;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import in.learnjavaskills.springsecurity.enums.Role;
import in.learnjavaskills.springsecurity.repository.UserdetailsRepository;
import in.learnjavaskills.springsecurity.security.filter.JwtTokenGenerationFilter;
import in.learnjavaskills.springsecurity.security.filter.JwtTokenVerification;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class APISecurityConfiguration 
{
	public final UserdetailsRepository userdetailRepository;
	
	public APISecurityConfiguration(UserdetailsRepository userdetailRepository)
	{
		this.userdetailRepository = userdetailRepository;
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
	{
		httpSecurity.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // disabling JSESSIONID token generation and validation which spring security usually does, now we are using JWT token
			.and()
				.cors().configurationSource(new CorsConfigurationSourceImpl()) // cross origin resource sharing
			.and()
				.csrf().ignoringAntMatchers("/register", "/login") // ignoring csrf for register api
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // Cross Site Request Forgery, with HTTPOnlyFalse means, toekn can send or capture usign javascrupt code as well
			.and()
//				.addFilterBefore(new RequestValidationFilter(), BasicAuthenticationFilter.class)
//				.addFilterAfter(new AuthenticateLogginFilter(), BasicAuthenticationFilter.class)
				
				.addFilterBefore(new JwtTokenVerification(new UserDetailsServiceImpl(userdetailRepository)), UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(new JwtTokenGenerationFilter(), BasicAuthenticationFilter.class)
				// for spring boot version 2.7 +
				.authorizeHttpRequests((auth) -> auth
//                        .antMatchers("/account/**","/balance/**").hasRole(Role.ROLE_ADMIN.getRole())
                        .antMatchers("/notice/**", "/interest-rate/**","/register").permitAll()
                ).httpBasic(Customizer.withDefaults());
				
		// for spring boor lesst then 2.7 version
//				.authorizeRequests()
//				.antMatchers("/account/**","/balance/**").hasRole("ADMIN")
////					.authenticated()
//				.antMatchers("/notice/**", "/interest-rate/**","/register")
//					.permitAll()
////			.and()
////				.formLogin()
//			.and()
//				.httpBasic();
		return httpSecurity.build();
	}
	
	
//	@Bean
//	InMemoryUserDetailsManager userDetailmanager()
//	{
//		UserDetails adminUserDetail = User.withDefaultPasswordEncoder()
//			.username("admin")
//			.password("admin")
//			.roles("admin")
//			.build();
//		
//		UserDetails userDetails = User.withDefaultPasswordEncoder()
//			.username("user")
//			.password("user")
//			.roles("user")
//			.build();
//		
//		return new InMemoryUserDetailsManager(adminUserDetail, userDetails);
//	}
	
	
	/**
	 * @param dataSource - spring boot will inject dataSource and the credential will be
	 * taken from the application.properties file
	 * @return JdbcuserDetailmanager
	 */
//	@Bean
//	UserDetailsService jdbcUserDetailService(DataSource dataSource)
//	{
//		return new JdbcUserDetailsManager(dataSource);
//	}
	
	
	/**
	 * This bean is created to instruct the spring security is my pass word is in plain text in db
	 * @return return instance of NoOpPasswordEncoder.
	 */
//	@Bean
//	PasswordEncoder passwordEncoder()
//	{
//		return NoOpPasswordEncoder.getInstance();
//	}
	
	/**
	 * @return BcryptPassowrd encoder with version 2Y and with 30 round 
	 */
	@Bean
	PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	
	
}