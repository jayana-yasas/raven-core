package com.example.demo.config.jwt;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.JwtUserDetailsService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {


        if (request.getRequestURI().equalsIgnoreCase("/authenticate")) {
            chain.doFilter(request, response);
        } else {
            final String requestTokenHeader = request.getHeader("Authorization");

            String userId = null;
            String jwtToken = null;

            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7);
                try {
                    userId = jwtTokenUtil.getUsernameFromToken(jwtToken);
                    Optional<User> otpUser = userRepository.findById(Long.parseLong(userId));

                    if (otpUser.isEmpty()) {
                        throw new RuntimeException("User Not Found");
                    }
                    request.setAttribute("traceId", UUID.randomUUID().toString());
                    request.setAttribute("userId", otpUser.get().getId());

                } catch (IllegalArgumentException e) {
                    System.out.println("Unable to get JWT Token");
                } catch (JwtException e) {
                    System.out.println("JWT Token error " + e.getMessage());
                }
            } else {
                System.out.println("JWT Token does not begin with Bearer String");
            }

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(userId);

                if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

                }
            }
            chain.doFilter(request, response);
        }
    }
}
