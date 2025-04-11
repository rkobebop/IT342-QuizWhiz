package cit.edu.quizwhiz.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class FirebaseAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String idToken = header.substring(7);
            try {
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
                // Set authentication in context if needed
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Firebase ID token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
