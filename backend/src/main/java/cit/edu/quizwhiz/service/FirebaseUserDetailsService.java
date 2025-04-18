package cit.edu.quizwhiz.service;

import cit.edu.quizwhiz.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FirebaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService; // Ensure this is injected by Spring

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Attempting to load user by username: " + username);

        try {
            Optional<UserEntity> userOpt = userService.findByEmail(username);

            if (userOpt.isEmpty()) {
                System.out.println("User not found for username: " + username);
                throw new UsernameNotFoundException("User not found");
            }

            UserEntity user = userOpt.get();
            System.out.println("User found: " + user);

            return User.builder()
                    .username(user.getEmail())
                    .password("") // Password is not needed for Firebase
                    .authorities("USER") // Add roles/authorities as needed
                    .build();

        } catch (Exception e) {
            System.out.println("Error loading user by username: " + e.getMessage());
            throw new UsernameNotFoundException("Error loading user", e);
        }
    }
}