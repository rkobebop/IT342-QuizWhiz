package cit.edu.quizwhiz.service;

import cit.edu.quizwhiz.entity.FlashcardEntity;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class FlashcardService {

    private final Firestore firestore;
    private static final String COLLECTION_NAME = "flashcards";

    public FlashcardService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    public List<FlashcardEntity> getAllFlashcards() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        return documents.stream()
                .map(doc -> doc.toObject(FlashcardEntity.class))
                .collect(Collectors.toList());
    }

    public Optional<FlashcardEntity> getFlashcardById(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        return document.exists() ? Optional.of(document.toObject(FlashcardEntity.class)) : Optional.empty();
    }

    public FlashcardEntity createFlashcard(FlashcardEntity flashcard) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(); // auto-generated ID
        flashcard.setId(docRef.getId());
        ApiFuture<WriteResult> future = docRef.set(flashcard);
        future.get(); // Wait for write to complete
        return flashcard;
    }

    public FlashcardEntity updateFlashcard(String id, FlashcardEntity flashcardDetails) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<WriteResult> future = docRef.set(flashcardDetails);
        future.get();
        return flashcardDetails;
    }

    public void deleteFlashcard(String id) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = firestore.collection(COLLECTION_NAME).document(id).delete();
        future.get();
    }
}