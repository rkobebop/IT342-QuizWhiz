package cit.edu.quizwhiz.controller;

import cit.edu.quizwhiz.entity.FlashCardEntity;
import cit.edu.quizwhiz.service.FlashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flashcards")
public class FlashCardController {

    @Autowired
    private FlashCardService flashCardService;

    @GetMapping("/")
    public String welcome() {
        return "Welcome to QuizWhiz!!";
    }

    @GetMapping
    public List<FlashCardEntity> getAllFlashCards() {
        return flashCardService.getAllFlashCards();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlashCardEntity> getFlashCardById(@PathVariable Long id) {
        return flashCardService.getFlashCardById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public FlashCardEntity createFlashCard(@RequestBody FlashCardEntity flashCard) {
        return flashCardService.createFlashCard(flashCard);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlashCardEntity> updateFlashCard(
            @PathVariable Long id, @RequestBody FlashCardEntity flashCardDetails) {
        return ResponseEntity.ok(flashCardService.updateFlashCard(id, flashCardDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlashCard(@PathVariable Long id) {
        flashCardService.deleteFlashCard(id);
        return ResponseEntity.noContent().build();
    }
}
