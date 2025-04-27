package cit.edu.quizwhiz.service;

import cit.edu.quizwhiz.entity.ProgressEntity;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class ProgressService {

    private final Firestore firestore;
    private static final String COLLECTION_NAME = "progress";

    public ProgressService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    public ProgressEntity createProgress(ProgressEntity progress) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document();
        progress.setProgressId(docRef.getId());
        progress.setCreatedAt(Timestamp.now());
        ApiFuture<WriteResult> future = docRef.set(progress);
        future.get();
        return progress;
    }

    public List<ProgressEntity> getAllProgress() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        return documents.stream()
                .map(doc -> doc.toObject(ProgressEntity.class))
                .collect(Collectors.toList());
    }

    public Optional<ProgressEntity> getProgressById(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        return document.exists() ? Optional.of(document.toObject(ProgressEntity.class)) : Optional.empty();
    }

    public List<ProgressEntity> getProgressByFlashcardId(String flashcardId) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME)
                .whereEqualTo("flashCardId", flashcardId)
                .get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        return documents.stream()
                .map(doc -> doc.toObject(ProgressEntity.class))
                .collect(Collectors.toList());
    }

    public ProgressEntity updateProgress(String id, ProgressEntity progressDetails) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(id);
        progressDetails.setProgressId(id); // Ensure ID is correct
        ApiFuture<WriteResult> future = docRef.set(progressDetails);
        future.get();
        return progressDetails;
    }

    public void deleteProgress(String id) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = firestore.collection(COLLECTION_NAME).document(id).delete();
        future.get();
    }
}