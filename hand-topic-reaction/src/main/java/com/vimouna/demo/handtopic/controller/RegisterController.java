package com.vimouna.demo.handtopic.controller;

import com.vimouna.demo.handtopic.dto.register.RegisterRequest;
import com.vimouna.demo.handtopic.dto.register.RegisterResponse;
import com.vimouna.demo.handtopic.services.RegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/register",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResponseEntity<RegisterResponse> index(@RequestBody @Valid RegisterRequest registerRequest) {
        return ResponseEntity.ok(registerService.save(registerRequest));
    }
}
