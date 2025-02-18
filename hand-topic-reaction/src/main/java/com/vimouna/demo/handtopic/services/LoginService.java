package com.vimouna.demo.handtopic.services;

import com.vimouna.demo.handtopic.dto.login.LoginRequest;
import com.vimouna.demo.handtopic.dto.login.LoginResponse;
import com.vimouna.demo.handtopic.exceptions.ServiceException;
import com.vimouna.demo.handtopic.repository.UserRepository;
import com.vimouna.demo.handtopic.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        try {
            if (!userRepository.existsByEmail(request.getUsername())) {
                throw new ServiceException("Email doest not exists");
            }

            UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken.unauthenticated(request.getUsername(), request.getPassword());
            Authentication authentication = authenticationManager.authenticate(authToken);

            if (!authentication.isAuthenticated()) {
                throw new UsernameNotFoundException("Incorrect password");
            }

            String token = jwtUtil.generate(request.getUsername(), 500000);
            return new LoginResponse(
                    token,
                    jwtUtil.extractCreatedAt(token),
                    jwtUtil.extractExpirationDate(token)
            );
        } catch (ServiceException e) {
            log.error("::: Service Exception when login process :::", e);
            throw new ServiceException(e.getMessage());
        } catch (BadCredentialsException e) {
            log.error("::: Service BadCredentialsException when login process :::", e);
            throw new ServiceException("Invalid Password/ Credential");
        } catch (Exception e) {
            log.error("::: Error when register user process :::", e);
            throw new ServiceException("Invalid login user process.");
        }
    }
}
