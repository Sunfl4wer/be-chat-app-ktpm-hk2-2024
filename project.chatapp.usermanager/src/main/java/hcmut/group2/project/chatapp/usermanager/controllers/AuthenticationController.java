package hcmut.group2.project.chatapp.usermanager.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import hcmut.group2.project.chatapp.usermanager.dto.AuthenticationResponseDto;
import hcmut.group2.project.chatapp.usermanager.dto.ChatUserLoginDto;
import hcmut.group2.project.chatapp.usermanager.dto.ChatUserRegistrationDto;
// import hcmut.group2.project.chatapp.usermanager.entities.ChatUser;
import hcmut.group2.project.chatapp.usermanager.services.AuthenticationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@Valid @RequestBody ChatUserRegistrationDto userRegistrationDto) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{phoneNumber}")
                .buildAndExpand(userRegistrationDto.getPhoneNumber())
                .toUri();
        
        return ResponseEntity.created(location).body(authService.register(userRegistrationDto));
        // return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@Valid @RequestBody ChatUserLoginDto request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}