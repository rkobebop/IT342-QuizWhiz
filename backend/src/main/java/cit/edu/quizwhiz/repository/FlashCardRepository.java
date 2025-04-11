package cit.edu.quizwhiz.repository;

import cit.edu.quizwhiz.model.FlashCard;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.util.concurrent.ExecutionException;

@Repository
public class FlashCardRepository {

    private static final Logger logger = LoggerFactory.getLogger(FlashCardRepository.class);
    private final Firestore db = FirestoreClient.getFirestore();

    public String saveFlashcard(String id, String question, String answer) {
        DocumentReference docRef = db.collection("flashcards").document(id);
        ApiFuture<WriteResult> result = docRef.set(new FlashCard(id, question, answer));

        try {
            return "Flashcard saved at: " + result.get().getUpdateTime();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error saving flashcard", e);
            Thread.currentThread().interrupt(); // Preserve thread state
            return "Error saving flashcard";
        }
    }
}