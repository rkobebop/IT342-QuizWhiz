package cit.edu.quizwhiz.controller;

import cit.edu.quizwhiz.entity.QuizWhizEntity;
import cit.edu.quizwhiz.service.QuizWhizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizwhiz")
public class QuizWhizController {

    @Autowired
    private QuizWhizService quizWhizService;

    @GetMapping("/")
    public String welcome() {
        return "Welcome to QuizWhiz!";
    }

    @GetMapping
    public List<QuizWhizEntity> getAllQuizWhiz() {
        return quizWhizService.getAllQuizWhizzes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizWhizEntity> getQuizWhizById(@PathVariable Long id) {
        return quizWhizService.getQuizWhizById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public QuizWhizEntity createQuizWhiz(@RequestBody QuizWhizEntity quizWhiz) {
        return quizWhizService.createQuizWhiz(quizWhiz);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizWhizEntity> updateQuizWhiz(
            @PathVariable Long id, @RequestBody QuizWhizEntity quizWhizDetails) {
        return ResponseEntity.ok(quizWhizService.updateQuizWhiz(id, quizWhizDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuizWhiz(@PathVariable Long id) {
        quizWhizService.deleteQuizWhiz(id);
        return ResponseEntity.noContent().build();
    }
}
