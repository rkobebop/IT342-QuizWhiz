package cit.edu.quizwhiz.service;

import cit.edu.quizwhiz.entity.FlashCardEntity;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class FlashCardService {

    private final Firestore firestore;

    public FlashCardService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    public List<FlashCardEntity> getAllFlashCards() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = firestore.collection("flashcards").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        return documents.stream()
                .map(doc -> doc.toObject(FlashCardEntity.class))
                .collect(Collectors.toList());
    }

    public Optional<FlashCardEntity> getFlashCardById(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("flashcards").document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        return document.exists() ? Optional.of(document.toObject(FlashCardEntity.class)) : Optional.empty();
    }

    public FlashCardEntity createFlashCard(FlashCardEntity flashCard) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = firestore.collection("flashcards")
                .document(flashCard.getId()).set(flashCard);
        future.get(); // Wait for Firestore write operation to complete
        return flashCard;
    }

    public FlashCardEntity updateFlashCard(String id, FlashCardEntity flashCardDetails) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("flashcards").document(id);
        ApiFuture<WriteResult> future = docRef.set(flashCardDetails);
        future.get();
        return flashCardDetails;
    }

    public void deleteFlashCard(String id) {
        firestore.collection("flashcards").document(id).delete();
    }
}