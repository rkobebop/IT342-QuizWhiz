package cit.edu.quizwhiz.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("firebase-adminsdk.json");

        if (serviceAccount == null) {
            throw new RuntimeException("Firebase credentials file not found!");
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://quizwhiz-flashcards-default-rtdb.firebaseio.com/") // ✅ Updated URL
                .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public Firestore firestore() throws IOException {
        return FirestoreClient.getFirestore(firebaseApp());
    }
}
