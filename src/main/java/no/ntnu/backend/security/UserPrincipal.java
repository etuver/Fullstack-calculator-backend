package no.ntnu.backend.security;

import no.ntnu.backend.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private Collection<GrantedAuthority> authorities;
    private String username;
    private String password;


    /**
     * Defines authorities of given user
     * @param user
     * @return
     */
    public static UserPrincipal of(User user){
        UserPrincipal userPrincipal = new UserPrincipal();

        userPrincipal.username = user.getEmail();
        userPrincipal.password = user.getPassword();
        return userPrincipal;
    }





    public UserPrincipal(){

    }

    public UserPrincipal(Collection<GrantedAuthority> authorities, String username, String password) {
        this.authorities = authorities;
        this.username = username;
        this.password = password;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
