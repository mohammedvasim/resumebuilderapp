package in.vasim.resumebuilderapi.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import in.vasim.resumebuilderapi.document.User;
import in.vasim.resumebuilderapi.dto.AuthResponse;
import in.vasim.resumebuilderapi.dto.RegisterRequest;
import in.vasim.resumebuilderapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    

    private final UserRepository userRepository;

    public AuthResponse register(RegisterRequest request){
        log.info("Inside Authservice: register(){}", request);
        // Check if user with the same email already exists

        if(userRepository.ExistsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }

        // Create a new user and save to the database

        User newUser= toDocument(request);
        
        userRepository.save(newUser);

        //TODO: Send verification email 



        return toresponse(newUser);

    }

    private AuthResponse toresponse(User newUser){
        return AuthResponse.builder()
            .id(newUser.getId())
            .name(newUser.getName())
            .email(newUser.getEmail())
            .profileImageUrl(newUser.getProfileImageUrl())
            .subscriptionPlan(newUser.getSubscriptionPlan())
            .emailVerified(Boolean.parseBoolean(newUser.getEmailVerified()))
            .createdAt(newUser.getCreatedAt())
            .updatedAt(newUser.getUpdatedAt())
            .build();
    }

    private User toDocument(RegisterRequest request){
        return User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(request.getPassword())
            .profileImageUrl(request.getProfileImageUrl())
            .subscriptionPlan("basic")
            .emailVerified("false")
            .verificationToken(UUID.randomUUID().toString())
            .verificationExpires(LocalDateTime.now().plusHours(24))
            .build();

    }

}
