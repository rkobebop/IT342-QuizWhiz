package cit.edu.quizwhiz.controller;

import cit.edu.quizwhiz.entity.UserEntity;
import cit.edu.quizwhiz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/success")
    public ResponseEntity<UserEntity> handleOAuthSuccess(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        String name = principal.getAttribute("name");

        try {
            UserEntity user = userService.createOrGetOAuthUser(email, name);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "OAuth login failed");
        }
    }
}
