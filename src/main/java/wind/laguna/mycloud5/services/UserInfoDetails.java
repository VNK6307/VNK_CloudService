package wind.laguna.mycloud5.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import wind.laguna.mycloud5.entities.UserInfo;

import java.util.Collection;
import java.util.List;

public class UserInfoDetails implements UserDetails {
    String username;
    String password;
    List<GrantedAuthority> authorities;

    public UserInfoDetails(UserInfo userInfo) {
        username = userInfo.getUsername();
        password = userInfo.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
