package hcmut.group2.project.chatapp.usermanager.configurations;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import hcmut.group2.project.chatapp.usermanager.entities.RefreshToken;
import hcmut.group2.project.chatapp.usermanager.repositories.RefreshTokenRepository;

@Configuration
public class CustomLogoutHandler implements LogoutHandler {

    private final RefreshTokenRepository tokenRepository;

    public CustomLogoutHandler(RefreshTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7);
        RefreshToken storedToken = tokenRepository.findByToken(token).orElse(null);

        if(storedToken != null) {
            storedToken.setLoggedOut(true);
            tokenRepository.save(storedToken);
        }
    }
}