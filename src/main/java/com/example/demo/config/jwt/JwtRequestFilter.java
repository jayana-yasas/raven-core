package com.example.demo.config.jwt;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.JwtUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    private Boolean checkIfAuthUri(String uri) {
        return uri.contains("user/auth");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        request.setAttribute("traceId", UUID.randomUUID().toString());
        if (request.getRequestURI().equalsIgnoreCase("/authenticate")) {
            chain.doFilter(request, response);
        }else if (checkIfAuthUri(request.getRequestURI())) {
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

                    request.setAttribute("userId", otpUser.get().getId());

                } catch (IllegalArgumentException e) {
                    System.out.println("Unable to get JWT Token");
                    Map<String, Object> errorDetails = new HashMap<>();
                    errorDetails.put("message", "Invalid token");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    mapper.writeValue(response.getWriter(), errorDetails);
                } catch (ExpiredJwtException e) {
                    System.out.println("JWT Token error " + e.getMessage());
                    Map<String, Object> errorDetails = new HashMap<>();
                    errorDetails.put("message", "Expired token");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    mapper.writeValue(response.getWriter(), errorDetails);

                } catch (JwtException e) {
                    System.out.println("JWT Token error " + e.getMessage());
                    Map<String, Object> errorDetails = new HashMap<>();
                    errorDetails.put("message", "Invalid token");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    mapper.writeValue(response.getWriter(), errorDetails);
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
