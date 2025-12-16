package com.example.banking.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    private JwtTokenHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    //this method will be called for each request
    //this method will check whether the request has valid token or not
    //if token is valid then set the authentication in security context
    //if token is not valid then do nothing and send the request to next filter in chain
    //OncePerRequestFilter ensures that the filter is only executed once per request
    //it provides a doFilterInternal method that we need to implement
    //it also provides a shouldNotFilter method that we can override to skip the filter for certain requests
    //like we can skip the filter for login request
    //but here we are not overriding it so the filter will be executed for all requests
    //if we want to skip the filter for certain requests then we can override the shouldNotFilter method
    //and return true for those requests
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Authorization
        String requestHeader = request.getHeader("Authorization");

        //Bearer 234dfgfdgdfgdfgdfgdfg
        logger.info("Header :  {}", requestHeader);

        String username = null;
        String token = null;

        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            //looking good
            token = requestHeader.substring(7);

            try {
                //fetch the username from token
                username = this.jwtHelper.getUsernameFromToken(token);
                logger.info("Username from token : {}", username);
            } catch (IllegalArgumentException e) {
                logger.info("Illegal Argument while fetching the username !!");
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                logger.info("Given jwt token is expired !!");
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                logger.info("Some changed has done in token !! Invalid Token");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            logger.info("Invalid Header Value !! ");
        }

        //once we get the token now validate - After validating(Successfully Login) we need to set the authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            //fetch user detail from username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            //validate token
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);

            if (validateToken) {
                //set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                //set details: details means from where the request is coming ip address, session id
                //so, this will help to fetch those details and set in authentication object
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //now, set the authentication in security context
                logger.info("Setting the security context with {}", authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } else {
                logger.info("Validation fails !!");
            }

        } else {
            logger.info("Username is null or context is not null");
        }

        //send the request to next filter in chain
        filterChain.doFilter(request, response);
    }
}
