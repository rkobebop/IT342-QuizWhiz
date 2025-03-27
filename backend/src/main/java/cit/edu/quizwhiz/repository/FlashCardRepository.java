package cit.edu.quizwhiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cit.edu.quizwhiz.entity.QuizWhizEntity;

@Repository
public interface QuizWhizRepository extends JpaRepository<QuizWhizEntity, Long> {
}