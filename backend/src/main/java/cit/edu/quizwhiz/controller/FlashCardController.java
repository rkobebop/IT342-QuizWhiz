package cit.edu.quizwhiz.controller;

import cit.edu.quizwhiz.entity.FlashCardEntity;
import cit.edu.quizwhiz.service.FlashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/flashcards")
public class FlashCardController {

    @Autowired
    private FlashCardService flashCardService;

    @GetMapping("/test")
    public String welcome() {
        return "Welcome to QuizWhiz!!";
    }

    @GetMapping
    public List<FlashCardEntity> getAllFlashCards() throws ExecutionException, InterruptedException {
        return flashCardService.getAllFlashCards();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlashCardEntity> getFlashCardById(@PathVariable String id) throws ExecutionException, InterruptedException {
        Optional<FlashCardEntity> flashCard = flashCardService.getFlashCardById(id);
        return flashCard.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FlashCardEntity> createFlashCard(@RequestBody FlashCardEntity flashCard) throws ExecutionException, InterruptedException {
        FlashCardEntity createdFlashCard = flashCardService.createFlashCard(flashCard);
        return ResponseEntity.ok(createdFlashCard);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlashCardEntity> updateFlashCard(@PathVariable String id, @RequestBody FlashCardEntity flashCardDetails) throws ExecutionException, InterruptedException {
        FlashCardEntity updatedFlashCard = flashCardService.updateFlashCard(id, flashCardDetails);
        return ResponseEntity.ok(updatedFlashCard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlashCard(@PathVariable String id) {
        flashCardService.deleteFlashCard(id);
        return ResponseEntity.noContent().build();
    }
}