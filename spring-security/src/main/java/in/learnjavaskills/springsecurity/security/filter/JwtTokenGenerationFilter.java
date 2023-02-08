package in.learnjavaskills.springsecurity.security.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import in.learnjavaskills.springsecurity.enums.Header;
import in.learnjavaskills.springsecurity.enums.JwtTokenPayload;
import in.learnjavaskills.springsecurity.enums.Secretkeys;
import in.learnjavaskills.springsecurity.enums.TokenPrefix;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtTokenGenerationFilter extends OncePerRequestFilter {

	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
		Authentication authentication = SecurityContextHolder.getContext()
			.getAuthentication();
		
		SecretKey secretKey = Keys.hmacShaKeyFor(getSha256BitKey());
//		SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
//		String secretString = Encoders.BASE64.encode(secretKey.getEncoded());
//		System.out.println("secretKey : " + secretString);
		
		System.out.println("Secretkeys.JWT_KEYS.getKey() : " + Secretkeys.JWT_KEYS.getKey());
		
		String jwtToken = Jwts.builder()
			.setIssuer("Imran.Shaikh@Citiustech.com")
			.setSubject("JWT token for authentication")
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
			.claim(JwtTokenPayload.username.name(), authentication.getName())
			.claim(JwtTokenPayload.authority.name(), getAuthority(authentication.getAuthorities()))
			.signWith(bs64key())
			.compact();
		jwtToken = TokenPrefix.prefix.getPrefixValue() + jwtToken;
		System.out.println("Generated token: " + jwtToken);
		
		response.setHeader(Header.Authorization.toString(), jwtToken);
		filterChain.doFilter(request, response);
	}
	
	private String getAuthority(Collection<? extends GrantedAuthority> authorities)
	{
		Set<String> authoritySet = new HashSet<String>();
		for (GrantedAuthority grantAuthority : authorities)
			authoritySet.add(grantAuthority.getAuthority());
		
		
		
		 String authority = String.join(",", authoritySet);
		 System.out.println("authority: " + authority);
		 return authority;
	}
	
	
	
	/**
	 * Generating JWT token only for the login api, for other api we are not generating because other api will be authenticated by the JWT token 
	 * and login api will get authenticated by the BasicAuthenticationFilter using username and password.
	 */
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return !request.getServletPath()
				.equalsIgnoreCase("/login");
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
	
	
	 private Key getSignInKey() {
	    byte[] keyBytes = Decoders.BASE64.decode(Secretkeys.JWT_KEYS.getKey());
	    return Keys.hmacShaKeyFor(keyBytes);
	 }
	 
	 private SecretKey bs64key()
	 {
		 return Keys.hmacShaKeyFor(Secretkeys.JWT_KEYS.getKey().getBytes(StandardCharsets.UTF_8));
	 }

}
