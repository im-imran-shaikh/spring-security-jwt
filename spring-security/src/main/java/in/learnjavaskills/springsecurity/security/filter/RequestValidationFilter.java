package in.learnjavaskills.springsecurity.security.filter;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.BadCredentialsException;

import in.learnjavaskills.springsecurity.enums.Header;

public class RequestValidationFilter implements Filter 
{
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException 
	{
		System.out.println("under RequestValidationFilter");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String authorization = httpServletRequest.getHeader(Header.Authorization.toString());
		
		if (Objects.isNull(authorization) || !authorization.contains("Basic "))
			throw new BadCredentialsException("Authorization token must be non null");
		
//		String base64Credentials = authorization.substring(6);
//		byte[] decodedCredential = Base64.getDecoder().decode(base64Credentials);
//		String plainTextCredentail = new String(decodedCredential);
//		if (!plainTextCredentail.contains(":"))
//			throw new BadCredentialsException("Delimeter not present in the credentials");
//		
//		String username = plainTextCredentail.split(":")[0];
//		if (!username.contains("@") || !username.contains("."))
//			throw new BadCredentialsException("invalid user");
//		
//		System.out.println("username: " + username);
		
		System.out.println("RequestValidationFilter success now basic authetication filter will execute");
		chain.doFilter(request, response);
	}

}
