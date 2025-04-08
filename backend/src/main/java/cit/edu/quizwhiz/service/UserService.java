package cit.edu.quizwhiz.service;

import cit.edu.quizwhiz.entity.UserEntity;
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
public class UserService {

    private final Firestore firestore;
    private static final String COLLECTION_NAME = "users";

    public UserService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    // Create a new user
    public UserEntity createUser(UserEntity user) throws ExecutionException, InterruptedException {
        user.setCreatedAt(Timestamp.now());
        user.setUpdatedAt(Timestamp.now());
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document();
        user.setUserId(docRef.getId());
        ApiFuture<WriteResult> future = docRef.set(user);
        future.get();  // Wait for operation to complete
        return user;
    }

    // Get all users
    public List<UserEntity> getAllUsers() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        System.out.println("Fetched documents: " + documents.size());

        return documents.stream()
                .map(doc -> {
                    try {
                        return doc.toObject(UserEntity.class);
                    } catch (Exception e) {
                        System.err.println("Error converting doc: " + doc.getId());
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(u -> u != null)
                .collect(Collectors.toList());
    }


    // Get user by ID
    public Optional<UserEntity> getUserById(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        return document.exists() ? Optional.of(document.toObject(UserEntity.class)) : Optional.empty();
    }

    // Update user
    public UserEntity updateUser(String id, UserEntity updatedUser) throws ExecutionException, InterruptedException {
        updatedUser.setUpdatedAt(Timestamp.now());
        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<WriteResult> future = docRef.set(updatedUser);
        future.get(); // Wait for the update to complete
        return updatedUser;
    }

    // Delete user
    public void deleteUser(String id) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = firestore.collection(COLLECTION_NAME).document(id).delete();
        future.get();
    }

    // Login user
    public Optional<UserEntity> loginUser(String email, String password) throws ExecutionException, InterruptedException {
        Query query = firestore.collection(COLLECTION_NAME)
                .whereEqualTo("email", email)
                .whereEqualTo("password", password);

        ApiFuture<QuerySnapshot> future = query.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        return documents.isEmpty() ? Optional.empty()
                : Optional.of(documents.get(0).toObject(UserEntity.class));
    }

    // Get user count
    public long getUserCount() throws ExecutionException, InterruptedException {
        return firestore.collection(COLLECTION_NAME).get().get().size();
    }
}
