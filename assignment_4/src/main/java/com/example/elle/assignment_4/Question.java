package com.example.elle.assignment_4;

/**
 * Created by Elle on 2015-09-22.
 */
public class Question {
    public String question;
    public String choice_one;
    public String choice_two;
    public String choice_three;
    public int answer;


    public Question(String question, String choice_one, String choice_two, String choice_three, int answer) {
        this.question = question;
        this.choice_one = choice_one;
        this.choice_two = choice_two;
        this.choice_three = choice_three;
        this.answer = answer;
    }
}
