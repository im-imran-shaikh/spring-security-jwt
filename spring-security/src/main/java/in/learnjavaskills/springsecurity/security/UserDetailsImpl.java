package in.learnjavaskills.springsecurity.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import in.learnjavaskills.springsecurity.entity.UserdetailsEntity;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;
	private UserdetailsEntity userdetailsEntity;
	
	public UserDetailsImpl(UserdetailsEntity userDetailEntity) {
		this.userdetailsEntity = userDetailEntity;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(userdetailsEntity.getRole().toString()));
	}

	@Override
	public String getPassword() {
		return userdetailsEntity.getPassword();
	}

	@Override
	public String getUsername() {
		return userdetailsEntity.getUseremail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return userdetailsEntity.isActive();
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !userdetailsEntity.isPasswordexpired();
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
