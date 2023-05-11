package br.com.brunogambim.pdf_repository.api.v1.security.entities;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.brunogambim.pdf_repository.core.user_management.entities.User;

public class UserSS implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String email;
	private String password;
	private Roles role;
	
	public UserSS(User user) {
		this.id = user.getId();
		this.email =user.getEmail();
		this.password = user.getPassword();
		this.role = Roles.fromClass(user);
	}
	
	public UserSS(Long id, String email, String password, Roles role) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public Long getId() {
		return this.id;
	}
	
	public String getRole() {
		return this.role.getDescription();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority(role.getSSDescription()));
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public boolean hasRole(Roles role) {
		return this.role == role;
	}

}
