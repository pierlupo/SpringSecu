package com.springsecu.controller;

import com.springsecu.dto.LoginRequestDTO;
import com.springsecu.dto.LoginResponseDTO;
import com.springsecu.model.Customer;
import com.springsecu.model.UserDetailsImpl;
import com.springsecu.repository.CustomerRepo;
import com.springsecu.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer){

        Customer c1 = null;
        ResponseEntity response = null;

        try{

            String hashPwd = passwordEncoder.encode(customer.getPwd());

            customer.setPwd(hashPwd);

            c1 = customerRepo.save(customer);

            if(c1.getId()>0){
                response = ResponseEntity.status(HttpStatus.CREATED).body("Customer registered successfully");

            }

        }catch(Exception ex){

            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error has occured : " + ex.getMessage());

        }
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequestDTO){

    try{
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(),loginRequestDTO.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    String email = userDetails.getUsername();

    int index = email.indexOf("@");

    String name = email.substring(0, index);

    String token = jwtUtil.generateJwtToken(authentication);

    String role = userDetails.getAuthorities().toArray()[0].toString().equals("ROLE_ADMIN")?"Administrator":"Customer";

    return ResponseEntity.ok(LoginResponseDTO.builder().token(token).id(userDetails.getId()).name(name).role(role).build());

    }catch(Exception e){

    throw e;

        }

    }

}
