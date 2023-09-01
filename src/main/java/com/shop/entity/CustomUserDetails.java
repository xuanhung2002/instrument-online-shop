package com.shop.entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CustomUserDetails implements UserDetails {
    /**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	

	private User user;
	
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // Mặc định mình sẽ để tất cả là ROLE_USER. Để demo cho đơn giản.
//        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    	
    	return Collections.singleton(new SimpleGrantedAuthority(this.getUser().getRole().getName()));
    	
    	
    }
    
	public CustomUserDetails(User user) {
		this.user = user;
	}
	
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
    public String getPassword() {
        return user.getAccount().getPassword();
    }

    @Override
    public String getUsername() {
        return user.getAccount().getUsername();
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
}