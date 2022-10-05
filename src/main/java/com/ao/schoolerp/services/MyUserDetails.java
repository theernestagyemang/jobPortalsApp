package com.ao.schoolerp.services;

import com.ao.schoolerp.entities.AppUser;
import com.ao.schoolerp.entities.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {
    private String username;
    private String password;
    private boolean isActive;

    private AppUser user; //to manage the current logged in user
    private List<GrantedAuthority> authorities = new ArrayList<>();

    public MyUserDetails(AppUser user){
        this.user = user;
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.isActive = user.isActive();
//        user.getRoles().stream().map(role->new SimpleGrantedAuthority(role.getName())).forEachOrdered(e->{
//            user.getRoles().add(e)
//        });
        user.getRoles().forEach( role ->{
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
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
        return isActive;
    }
}
