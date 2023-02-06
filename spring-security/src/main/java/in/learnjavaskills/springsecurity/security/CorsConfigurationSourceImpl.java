package in.learnjavaskills.springsecurity.security;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import in.learnjavaskills.springsecurity.enums.Header;

public class CorsConfigurationSourceImpl implements CorsConfigurationSource 
{
	@Override
	public CorsConfiguration getCorsConfiguration(HttpServletRequest request) 
	{
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
		corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
		corsConfiguration.setExposedHeaders(Collections.singletonList(Header.Authorization.name())); // sending Authorization header to other browser because we have implemented JWT
		corsConfiguration.setMaxAge(3600L); // age in second, means how long this setting should cache in the browser,
		//usually in browser we can set 7 days or 1 days as per the deployment cycle
		return corsConfiguration;
	}

}
