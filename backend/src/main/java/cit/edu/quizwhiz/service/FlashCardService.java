package cit.edu.quizwhiz.service;

import cit.edu.quizwhiz.repository.FlashCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cit.edu.quizwhiz.entity.FlashCardEntity;
import java.util.List;
import java.util.Optional;

@Service
public class FlashCardService {

    private final FlashCardRepository flashCardRepository;

    @Autowired
    public FlashCardService(FlashCardRepository flashCardRepository) {
        this.flashCardRepository = flashCardRepository;
    }

    public List<FlashCardEntity> getAllFlashCards() {
        return flashCardRepository.findAll();
    }

    public Optional<FlashCardEntity> getFlashCardById(Long id) {
        return flashCardRepository.findById(id);
    }

    public FlashCardEntity createFlashCard(FlashCardEntity flashCard) {
        return flashCardRepository.save(flashCard);
    }

    public FlashCardEntity updateFlashCard(Long id, FlashCardEntity flashCardDetails) {
        FlashCardEntity flashCard = flashCardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FlashCard not found with id " + id));

        flashCard.setStudentID(flashCardDetails.getStudentID());
        flashCard.setSubject(flashCardDetails.getSubject());
        flashCard.setCategory(flashCardDetails.getCategory());

        return flashCardRepository.save(flashCard);
    }

    public void deleteFlashCard(Long id) {
        flashCardRepository.deleteById(id);
    }
}
