package in.learnjavaskills.springsecurity.security.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.List;
import java.util.Objects;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import in.learnjavaskills.springsecurity.entity.UserdetailsEntity;
import in.learnjavaskills.springsecurity.enums.Header;
import in.learnjavaskills.springsecurity.enums.JwtTokenPayload;
import in.learnjavaskills.springsecurity.enums.Secretkeys;
import in.learnjavaskills.springsecurity.enums.TokenPrefix;
import in.learnjavaskills.springsecurity.repository.UserdetailsRepository;
import in.learnjavaskills.springsecurity.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;


public class JwtTokenVerification extends OncePerRequestFilter {

	private final UserDetailsServiceImpl userDetailsService;
	
	public JwtTokenVerification(UserDetailsServiceImpl userDetailsService)
	{
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		
		String jwtToken = request.getHeader(Header.Authorization.name());
		System.out.println("Jwt token: " + jwtToken);
		if (Objects.isNull(jwtToken) || jwtToken.isEmpty() || !jwtToken.startsWith(TokenPrefix.prefix.getPrefixValue()))
			throw new BadCredentialsException("JWT token must be non null and non empty and must contain valid token prefix");
		
			
		System.out.println("jwtToken.replace(TokenPrefix.prefix.getPrefixValue(), \"\") : " + jwtToken.replace(TokenPrefix.prefix.getPrefixValue(), ""));		
		
		// validating JWT Token, using jwt library, this will validate all is token expire etc 
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(bs64key())
			.build()
			.parseClaimsJws(jwtToken.replace(TokenPrefix.prefix.getPrefixValue(), "")) // matching the hash value of the JWT Token
			.getBody();
		
		String username = claims.get(JwtTokenPayload.username.name())
			.toString();
		
		String authorityInCommaSeperatedValue = claims.get(JwtTokenPayload.authority.name())
			.toString();
		
		System.out.println("authorityInCommaSeperatedValue : " + authorityInCommaSeperatedValue + " username : " + username);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		
		// Creating the username password authentication token with the username and authority,now spring secuirty will believe user has been authentication
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, 
				userDetails.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		
		// setting the authentication into the security context holder
		System.out.println("authentication.getname() : " + authentication.getName() + " authentication.getAuthority() : " + authentication.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	
		filterChain.doFilter(request, response);
	}
	
	/**
	 * Not authentication login api using the JWT token because, login api aother open apis will get authenticated by the basic authentication filter.
	 * Using the login api we are generating the JWT token
	 */
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		System.out.println("request.getServletPath() : " + request.getServletPath());
		String servletPath = request.getServletPath();
		
		if (servletPath.equalsIgnoreCase("/login") ||  servletPath.equalsIgnoreCase("/notice") ||
				servletPath.equalsIgnoreCase("/interest-rate/saving") || servletPath.equalsIgnoreCase("/register"))
			return true;
		return false;
	}
	
	
	 private SecretKey bs64key()
	 {
		 return Keys.hmacShaKeyFor(Secretkeys.JWT_KEYS.getKey().getBytes(StandardCharsets.UTF_8));
	 }
}
