package com.springsecu.config;

import com.springsecu.model.Authority;
import com.springsecu.model.Customer;
import com.springsecu.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
//fichier demo pour se logger avec le form de spring security par défaut
@Component
public class BankUserNamePwdAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String  email = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        List<Customer> customerList = (List<Customer>) customerRepo.findByEmail(email);

        //verif si il y a un utilisateur
        if(customerList.size()>0){
            //verif si mdp correspond
            if(passwordEncoder.matches(pwd, customerList.get(0).getPwd())){
                List<GrantedAuthority> authorities = new ArrayList<>();
                return new UsernamePasswordAuthenticationToken(email, pwd, getGrantedAuthorities(customerList.get(0).getAuthorities()));
            } else {
                throw new BadCredentialsException("invalid password");
            }
        } else {
            throw new BadCredentialsException("user doesn't exist");
        }
    }

    private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(Authority authority : authorities){
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
