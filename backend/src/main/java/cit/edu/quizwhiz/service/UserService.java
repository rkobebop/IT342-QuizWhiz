package cit.edu.quizwhiz.service;

import cit.edu.quizwhiz.entity.UserEntity;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final Firestore firestore;
    private static final String COLLECTION_NAME = "users";

    public UserService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    // Create a new user
    public UserEntity createUser(UserEntity user) throws ExecutionException, InterruptedException {
        user.setCreatedAt(LocalDateTime.now());
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(String.valueOf(user.getUserId()));
        ApiFuture<WriteResult> future = docRef.set(user);
        future.get(); // Wait for operation to complete
        return user;
    }

    // Get all users
    public List<UserEntity> getAllUsers() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME).get();
        return future.get().getDocuments().stream()
                .map(doc -> doc.toObject(UserEntity.class))
                .collect(Collectors.toList());
    }

    // Get user by ID
    public Optional<UserEntity> getUserById(int id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(String.valueOf(id));
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        return document.exists() ? Optional.of(document.toObject(UserEntity.class)) : Optional.empty();
    }

    // Update user
    public UserEntity updateUser(int id, UserEntity updatedUser) throws ExecutionException, InterruptedException {
        updatedUser.setUpdatedAt(LocalDateTime.now());
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(String.valueOf(id));
        ApiFuture<WriteResult> future = docRef.set(updatedUser);
        future.get();
        return updatedUser;
    }

    // Delete user
    public void deleteUser(int id) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = firestore.collection(COLLECTION_NAME).document(String.valueOf(id)).delete();
        future.get();
    }

    // Login user
    public Optional<UserEntity> loginUser(String email, String password) throws ExecutionException, InterruptedException {
        Query query = firestore.collection(COLLECTION_NAME)
                .whereEqualTo("email", email)
                .whereEqualTo("password", password);

        ApiFuture<QuerySnapshot> future = query.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        return documents.isEmpty()
                ? Optional.empty()
                : Optional.of(documents.get(0).toObject(UserEntity.class));
    }

    // Get user count
    public long getUserCount() throws ExecutionException, InterruptedException {
        return firestore.collection(COLLECTION_NAME).get().get().size();
    }
}