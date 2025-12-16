package com.example.banking.controller;

import com.example.banking.dto.JwtAuthRequest;
import com.example.banking.dto.JwtAuthResponse;
import com.example.banking.dto.UserDto;
import com.example.banking.security.JwtTokenHelper;
import com.example.banking.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

//Authentication Controller: It will handle the login request
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtTokenHelper helper;

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    //Login API: It will take the email and password and return the token
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody JwtAuthRequest request) {

        // Calling doAuthenticate() Method: For Authenticate the user
        this.doAuthenticate(request.getUsername(), request.getPassword());

        // If authentication is successful then generate the token
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        // Generate JWT token using the user details
        String token = this.helper.generateToken(userDetails);

        Date expirationDateFromToken = this.helper.getExpirationDateFromToken(token);

        // Log the generated token (for demonstration purposes only; avoid logging sensitive information in production)
        logger.info("Generated Token: {}", token);

        // Log the token expiration date
        logger.info("Token Expiration Date: {}", expirationDateFromToken);

        // Return the token in the response
        JwtAuthResponse response = JwtAuthResponse.builder().jwtToken(token).username(userDetails.getUsername()).build();

        // Return the response entity with the JWT token and HTTP status OK
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Authenticate the user
    private void doAuthenticate(String email, String password) {
        // Create an authentication token using the provided email and password
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);

        try {
            // Use the AuthenticationManager to authenticate the user
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or Password !!");
        }
    }

    // Exception Handler for BadCredentialsException: This method will handle the BadCredentialsException and return a custom message
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }


    // Register API: It will take the user details and return the registered user details
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {

        UserDto registeredUser = userService.registerUser(userDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}