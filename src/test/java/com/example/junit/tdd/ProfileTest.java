package com.example.junit.tdd;

import com.example.junit.ch2.Answer;
import com.example.junit.ch2.Bool;
import com.example.junit.ch2.BooleanQuestion;
import com.example.junit.ch2.Criterion;
import com.example.junit.ch2.Question;
import com.example.junit.ch2.Weight;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProfileTest {
    @Test
    void matchesNotingWhenProfileEmpty() {
        Profile profile = new Profile();
        Question question = new BooleanQuestion(1, "Relocation package?");
        Criterion criterion = new Criterion(new Answer(question, Bool.TRUE), Weight.DontCare);

        boolean result = profile.matches(criterion);

        assertFalse(result);
    }

    @Test
    void matchesWhenProfileContainsMatchingAnswer() {
        Profile profile = new Profile();
        Question question = new BooleanQuestion(1, "Relocation package?");
        Answer answer = new Answer(question, Bool.TRUE);
        profile.add(answer);
        Criterion criterion = new Criterion(new Answer(question, Bool.TRUE), Weight.Important);

        boolean result = profile.matches(criterion);

        assertTrue(result);
    }

}