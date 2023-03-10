package in.learnjavaskills.springsecurity.security.filter;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticateLogginFilter implements Filter
{

	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException 
	{
		Authentication authentication = SecurityContextHolder.getContext()
			.getAuthentication();
		
		if (Objects.nonNull(authentication))
			System.out.println("username : " + authentication.getName() + " with role : " + authentication.getAuthorities() + " has been successfully logged");
		
		
		chain.doFilter(request, response);
	}

}
