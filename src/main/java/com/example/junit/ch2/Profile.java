/***
 * Excerpted from "Pragmatic Unit Testing in Java with JUnit",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/utj2 for more book information.
 ***/
package com.example.junit.ch2;

import com.example.junit.ch9.MatchSet;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class Profile {
    private Map<String, Answer> answers = new HashMap<>();
    private int score;
    @Getter
    private String name;

    public Profile(String name) {
        this.name = name;
    }

    public void add(Answer answer) {
        answers.put(answer.getQuestionText(), answer);
    }

    public boolean matches(Criteria criteria) {
        MatchSet matchSet = new MatchSet(answers, criteria);
        score = matchSet.getScore();
        return matchSet.matches();
    }
}
