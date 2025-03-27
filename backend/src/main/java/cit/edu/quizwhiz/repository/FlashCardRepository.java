package cit.edu.quizwhiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cit.edu.quizwhiz.entity.FlashCardEntity;

@Repository
public interface FlashCardRepository extends JpaRepository<FlashCardEntity, Long> {
}