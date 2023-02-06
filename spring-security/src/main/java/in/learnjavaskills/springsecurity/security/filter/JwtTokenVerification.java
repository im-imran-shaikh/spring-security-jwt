package in.learnjavaskills.springsecurity.security.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
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
import org.springframework.web.filter.OncePerRequestFilter;

import in.learnjavaskills.springsecurity.enums.Header;
import in.learnjavaskills.springsecurity.enums.JwtTokenPayload;
import in.learnjavaskills.springsecurity.enums.Secretkeys;
import in.learnjavaskills.springsecurity.enums.TokenPrefix;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

public class JwtTokenVerification extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{
		try
		{
		String jwtToken = request.getHeader(Header.Authorization.name());
		System.out.println("Jwt token: " + jwtToken);
		if (Objects.isNull(jwtToken) || jwtToken.isEmpty() || !jwtToken.startsWith(TokenPrefix.prefix.getPrefixValue()))
			throw new BadCredentialsException("JWT token must be non null and non empty and must contain valid token prefix");
		
	
		
		SecretKey secretKey = Keys.hmacShaKeyFor(getSha256BitKey());
//		SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
//		String secretString = Encoders.BASE64.encode(secretKey.getEncoded());
//		System.out.println("secretKey : " + secretString);
//		
		
System.out.println("jwtToken.replace(TokenPrefix.prefix.getPrefixValue(), \"\") : " + jwtToken.replace(TokenPrefix.prefix.getPrefixValue(), ""));		
		
		// validating JWT Token, using jwt library, this will validate all is token expire etc 
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(jwtToken.replace(TokenPrefix.prefix.getPrefixValue(), "")) // matching the hash value of the JWT Token
			.getBody();
		
		String username = claims.get(JwtTokenPayload.username.name())
			.toString();
		
		String authorityInCommaSeperatedValue = claims.get(JwtTokenPayload.authority.name())
			.toString();
		
		System.out.println("authorityInCommaSeperatedValue : " + authorityInCommaSeperatedValue + " username : " + username);
		
		// Creating the username password authentication token with the username and authority,now spring secuirty will believe user has been authentication
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, 
				AuthorityUtils.commaSeparatedStringToAuthorityList(authorityInCommaSeperatedValue));
		
		
		// setting the authentication into the security context holder
		System.out.println("authentication.getname() : " + authentication.getName() + " authentication.getAuthority() : " + authentication.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("authentication.getname() : " + authentication2.getName() + " authentication.getAuthority() : " + authentication2.getAuthorities() + " principal : " + authentication2.getPrincipal());
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			
		}
		filterChain.doFilter(request, response);
	}
	
	/**
	 * Not authentication login api using the JWT token because, login api will get authenticated by the basic authentication filter.
	 * Using the login api we are generating the JWT token
	 */
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		System.out.println("request.getServletPath() : " + request.getServletPath());
		return request.getServletPath().equalsIgnoreCase("/login");
	}
	
	private byte[] getSha256BitKey() 
	{
		try
		{
			 MessageDigest md = MessageDigest.getInstance("SHA-256");
		     return md.digest(Secretkeys.JWT_KEYS.getKey().getBytes(StandardCharsets.UTF_8));
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			return null;
		}
	}

}
