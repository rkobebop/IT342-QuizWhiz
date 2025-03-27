package cit.edu.quizwhiz.service;

import cit.edu.quizwhiz.repository.QuizWhizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cit.edu.quizwhiz.entity.QuizWhizEntity;
import java.util.List;
import java.util.Optional;

@Service
public class QuizWhizService {

    private final QuizWhizRepository quizWhizRepository;

    @Autowired
    public QuizWhizService(QuizWhizRepository quizWhizRepository) {
        this.quizWhizRepository = quizWhizRepository;
    }

    public List<QuizWhizEntity> getAllQuizWhizzes() {
        return quizWhizRepository.findAll();
    }

    public Optional<QuizWhizEntity> getQuizWhizById(Long id) {
        return quizWhizRepository.findById(id);
    }

    public QuizWhizEntity createQuizWhiz(QuizWhizEntity quizWhiz) {
        return quizWhizRepository.save(quizWhiz);
    }

    public QuizWhizEntity updateQuizWhiz(Long id, QuizWhizEntity quizWhizDetails) {
        QuizWhizEntity quizWhiz = quizWhizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QuizWhiz not found with id " + id));

        quizWhiz.setStudentID(quizWhizDetails.getStudentID());
        quizWhiz.setSubject(quizWhizDetails.getSubject());
        quizWhiz.setCategory(quizWhizDetails.getCategory());

        return quizWhizRepository.save(quizWhiz);
    }

    public void deleteQuizWhiz(Long id) {
        quizWhizRepository.deleteById(id);
    }
}
