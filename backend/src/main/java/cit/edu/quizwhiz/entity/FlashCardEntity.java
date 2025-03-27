package cit.edu.quizwhiz.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "quizwhiz")
public class QuizWhizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizWhizID;

    @Column(nullable = false)
    private Long studentID;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String category;

    // Getters and Setters
    public Long getFlashCardID() {
        return quizWhizID;
    }

    public void setFlashCardID(Long flashCardID) {
        this.quizWhizID = flashCardID;
    }

    public Long getStudentID() {
        return studentID;
    }

    public void setStudentID(Long studentID) {
        this.studentID = studentID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

