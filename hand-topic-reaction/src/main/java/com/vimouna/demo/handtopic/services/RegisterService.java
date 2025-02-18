package com.vimouna.demo.handtopic.services;

import com.vimouna.demo.handtopic.dto.register.RegisterRequest;
import com.vimouna.demo.handtopic.dto.register.RegisterResponse;
import com.vimouna.demo.handtopic.entities.UserEntity;
import com.vimouna.demo.handtopic.exceptions.ServiceException;
import com.vimouna.demo.handtopic.repository.UserRepository;
import com.vimouna.demo.handtopic.services.mapper.LoginMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterService {
    private final UserRepository userRepository;
    private final LoginMapper loginMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RegisterResponse save(RegisterRequest registerRequest) {
        try {
            if (userRepository.existsByEmail(registerRequest.getEmail())) {
                throw new ServiceException("Email already exists");
            }
            UserEntity userEntity = loginMapper.toUserEntity(registerRequest);
            userEntity.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            userRepository.save(userEntity);
            return RegisterResponse.builder()
                    .email(registerRequest.getEmail())
                    .build();
        } catch (ServiceException e) {
            log.error("::: Service Exception when register user process :::", e);
            throw new ServiceException(e.getMessage());
        } catch (Exception e) {
            log.error("::: Error when register user process :::", e);
            throw new ServiceException("Invalid register user process.");
        }
    }
}
