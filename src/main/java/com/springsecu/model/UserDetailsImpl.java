package com.springsecu.model;

import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;


@ToString
public class UserDetailsImpl implements UserDetails {

    private int id;

    private String userName;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public UserDetailsImpl(int id, String userName, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
    }


    public static UserDetailsImpl build(Customer customer){
        Collection<? extends GrantedAuthority> grantedAuthorities = customer.getAuthorities().stream().map(authority ->  new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
        return new UserDetailsImpl(customer.getId(), customer.getEmail(), customer.getPwd(), grantedAuthorities);
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
        return true;
    }


}
