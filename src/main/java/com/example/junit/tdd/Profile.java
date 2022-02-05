package com.example.junit.tdd;

import com.example.junit.ch2.Answer;
import com.example.junit.ch2.Criteria;
import com.example.junit.ch2.Criterion;
import com.example.junit.ch2.Weight;

import java.util.HashMap;
import java.util.Map;

public class Profile {
    private Map<String, Answer> answers = new HashMap<>();
    boolean matches = false;

    public boolean matches(Criteria criteria) {
        for (Criterion criterion : criteria) {
            if (matches((criterion))) {
                matches = true;
            } else if (criterion.getWeight() == Weight.MustMatch) {
                return false;
            }
        }
        return matches;
    }

    public boolean matches(Criterion criterion) {
        Answer answer = getMatchingProfileAnswer(criterion);
        return criterion.getAnswer().match(answer);
    }

    public void add(Answer answer) {
        answers.put(answer.getQuestionText(), answer);
    }

    private Answer getMatchingProfileAnswer(Criterion criterion) {
        return answers.get(criterion.getAnswer().getQuestionText());
    }

}
