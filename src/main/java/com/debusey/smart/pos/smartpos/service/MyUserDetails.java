/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos.service;

import com.debusey.smart.pos.smartpos.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private String userName;
    private String password;
    private boolean active;
    private Users user;
    private List<GrantedAuthority> authorities;

    public MyUserDetails(Users user) {

        this.userName = user.getUsername();
        this.password = user.getPassword();
        this.active = user.isActive();
        this.user = user;
        List<GrantedAuthority> sm = new ArrayList<>();

        user.getRoles().stream().map((r) -> new SimpleGrantedAuthority(r.getName())).forEachOrdered((e) -> {
            sm.add(e);
        });
        authorities = sm;


    }

    public MyUserDetails() {

    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
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
        return userName;
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
        return active;
    }

}
