package hcmut.group2.project.chatapp.usermanager.services;

import hcmut.group2.project.chatapp.usermanager.repositories.ChatUserRepository;
import hcmut.group2.project.chatapp.usermanager.repositories.RefreshTokenRepository;
import hcmut.group2.project.chatapp.usermanager.dto.AuthenticationResponseDto;
import hcmut.group2.project.chatapp.usermanager.dto.ChatUserLoginDto;
import hcmut.group2.project.chatapp.usermanager.dto.ChatUserRegistrationDto;
import hcmut.group2.project.chatapp.usermanager.entities.ChatUser;
import hcmut.group2.project.chatapp.usermanager.entities.RefreshToken;
import hcmut.group2.project.chatapp.usermanager.enums.UserActivity;
import hcmut.group2.project.chatapp.usermanager.enums.UserRole;
import hcmut.group2.project.chatapp.usermanager.enums.UserStatus;
import hcmut.group2.project.chatapp.usermanager.exceptions.IncorrectCredentialException;
import hcmut.group2.project.chatapp.usermanager.exceptions.UserDuplicatedException;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthenticationService {
    @Autowired
    private ChatUserRepository chatUserRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenRepository tokenRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponseDto register(ChatUserRegistrationDto userRegistrationDto) throws UserDuplicatedException {
        // check if user already exist. if exist than authenticate the user
        if(chatUserRepo.findByPhoneNumber(userRegistrationDto.getPhoneNumber()).isPresent() ||
            chatUserRepo.findByUsername(userRegistrationDto.getUsername()).isPresent()) {
            throw new UserDuplicatedException("User is already registered. Check your username and phone number.");
        }

        LocalDateTime nowTime = LocalDateTime.now();

        ChatUser user = ChatUser.builder().username(userRegistrationDto.getUsername())
                // .password(userRegistrationDto.getPassword())
                .password(passwordEncoder.encode(userRegistrationDto.getPassword()))
                .phoneNumber(userRegistrationDto.getPhoneNumber())
                .role(UserRole.USER)
                .status(UserStatus.ACTIVE)
                .activity(UserActivity.OFFLINE)
                .emailAddress(userRegistrationDto.getEmailAddress())
                .firstName(userRegistrationDto.getFirstName())
                .lastName(userRegistrationDto.getLastName())
                .birthDate(userRegistrationDto.getBirthDate())
                .creationDatetime(nowTime)
                .modificationDatetime(nowTime).build();

        int registerdUserId =  chatUserRepo.save(user).getId();
        String jwt = jwtService.generateToken(user);
        saveUserToken(jwt, user);

        return new AuthenticationResponseDto(jwt, "User registration was successful", Integer.toString(registerdUserId));

    }

    public AuthenticationResponseDto authenticate(ChatUserLoginDto userLoginDto) throws IncorrectCredentialException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDto.getPhoneNumber(),
                            userLoginDto.getPassword()
                    )
                );
        } catch (AuthenticationException ex) {
            throw new IncorrectCredentialException("Wrong phone number or password.");
        }

        ChatUser user = chatUserRepo.findByPhoneNumber(userLoginDto.getPhoneNumber()).orElseThrow();
        String jwt = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);

        return new AuthenticationResponseDto(jwt, "User login was successful", Integer.toString(user.getId()));

    }

    private void revokeAllTokenByUser(ChatUser user) {
        List<RefreshToken> validTokens = tokenRepo.findAllTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepo.saveAll(validTokens);
    }

    private void saveUserToken(String jwt, ChatUser user) {
        RefreshToken token = new RefreshToken();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepo.save(token);
    }
}