    package cit.edu.quizwhiz.controller;

    import cit.edu.quizwhiz.entity.UserEntity;
    import cit.edu.quizwhiz.security.JwtUtil;
    import cit.edu.quizwhiz.service.UserService;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseAuthException;
    import com.google.firebase.auth.FirebaseToken;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.core.annotation.AuthenticationPrincipal;
    import org.springframework.security.oauth2.core.user.OAuth2User;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.server.ResponseStatusException;


    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import java.util.Optional;
    import java.util.concurrent.ExecutionException;

    @RestController
    @RequestMapping("/user")
    public class UserController {

        JwtUtil jwtUtil = new JwtUtil();


        @Autowired
        private UserService userService;

        @GetMapping("/welcome")
        public String welcome() {
            return "Welcome to the User API!";
        }

        // Create a new user
        @PostMapping("/create")
        public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
            try {
                return ResponseEntity.ok(userService.createUser(user));
            } catch (Exception e) {
                e.printStackTrace(); // temporary logging for debugging
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating user: " + e.getMessage());
            }
        }

        //testing ra ni gamit thymeleaf
    //    @PostMapping("/create")
    //    public ResponseEntity<UserEntity> createUser(@ModelAttribute UserEntity user) {
    //        try {
    //            return ResponseEntity.ok(userService.createUser(user));
    //        } catch (Exception e) {
    //            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating user: " + e.getMessage());
    //        }
    //    }


        // Read all users
        @GetMapping("/all")
        public ResponseEntity<List<UserEntity>> getAllUsers() {
            try {
                return ResponseEntity.ok(userService.getAllUsers());
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching users: " + e.getMessage());
            }
        }

        // Read user by ID
        @GetMapping("/{userId}")
        public ResponseEntity<UserEntity> getUserById(@PathVariable String userId) {
            try {
                Optional<UserEntity> user = userService.getUserById(userId);
                return user.map(ResponseEntity::ok)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching user: " + e.getMessage());
            }
        }

        // Update user details
        @PutMapping("/update/{userId}")
        public ResponseEntity<UserEntity> updateUserDetails(@PathVariable String userId, @RequestBody UserEntity newUserDetails) {
            try {
                return ResponseEntity.ok(userService.updateUser(userId, newUserDetails));
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating user: " + e.getMessage());
            }
        }

        // Delete user by ID
        @DeleteMapping("/{userId}")
        public ResponseEntity<String> deleteUser(@PathVariable String userId) {
            try {
                userService.deleteUser(userId);
                return ResponseEntity.ok("User deleted successfully");
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting user: " + e.getMessage());
            }
        }

        @PostMapping("/login")
        public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
            String email = credentials.get("email");
            String password = credentials.get("password");

            try {
                Optional<UserEntity> userOpt = userService.loginUser(email, password);

                if (userOpt.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }

                UserEntity user = userOpt.get();
                String jwtToken = jwtUtil.generateToken(user.getEmail());

                Map<String, Object> response = new HashMap<>();
                response.put("id", user.getUserId());
                response.put("email", user.getEmail());
                response.put("token", jwtToken);

                return ResponseEntity.ok()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)  // ✅ Add this
                        .body(response);

            } catch (ExecutionException | InterruptedException | FirebaseAuthException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }


        @PostMapping("/google")
        public ResponseEntity<?> loginWithGoogle(@RequestBody Map<String, String> payload) {
            System.out.println("Received Google login request. Payload keys: " + payload.keySet()); // Log received payload

            String idToken = payload.get("credential");
            System.out.println("Extracted ID token: " + (idToken != null ? "present" : "null")); // Don't log actual token

            if (idToken == null || idToken.isEmpty()) {
                System.out.println("ID token is missing");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID token is required");
            }

            try {
                System.out.println("Attempting to verify ID token...");
                // Verify the Google ID token
                FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
                System.out.println("Token verified. Email: " + firebaseToken.getEmail());

                // Extract user information from the token
                String email = firebaseToken.getEmail();
                String name = firebaseToken.getName(); // Full name

                // Create or retrieve the user from Firestore
                UserEntity user = userService.createOrGetOAuthUser(email, name);

                // Generate JWT token for the user
                String jwt = jwtUtil.generateToken(user.getEmail());

                // Prepare response data
                Map<String, Object> response = new HashMap<>();
                response.put("user", user);
                response.put("token", jwt);

                return ResponseEntity.ok(response);

            } catch (FirebaseAuthException e) {
                System.err.println("Firebase auth error: " + e.getMessage());
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Google login failed: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
            }
        }



        // Get user count
        @GetMapping("/usercount")
        public ResponseEntity<Long> getUserCount() {
            try {
                return ResponseEntity.ok(userService.getUserCount());
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error counting users: " + e.getMessage());
            }
        }
    }