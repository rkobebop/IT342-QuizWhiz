package cit.edu.quizwhiz.entity;

public class FlashCardEntity {
    private String id;  // Use String ID instead of Long
    private String studentID;
    private String subject;
    private String category;

    public FlashCardEntity() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStudentID() { return studentID; }
    public void setStudentID(String studentID) { this.studentID = studentID; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
