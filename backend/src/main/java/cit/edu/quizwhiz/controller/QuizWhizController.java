package cit.edu.quizwhiz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(method = RequestMethod.GET,path="")

public class QuizWhizController {

    @GetMapping("/")
    public String Welcome(){
        return "Welcome to QuizWhiz!!!!";
    }
}
