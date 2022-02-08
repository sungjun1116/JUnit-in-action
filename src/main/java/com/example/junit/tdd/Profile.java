package com.example.junit.tdd;

import com.example.junit.ch2.Answer;
import com.example.junit.ch2.Criteria;
import com.example.junit.ch2.Criterion;
import com.example.junit.ch2.Weight;

public class Profile {
    private AnswerCollection answers = new AnswerCollection();
    boolean matches;

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
        return criterion.getWeight() == Weight.DontCare ||
                criterion.matches(answers.getMatchingAnswer(criterion));
    }

    public void add(Answer answer) {
        answers.add(answer);
    }

    public ProfileMatch match(Criteria criteria) {
        return new ProfileMatch(answers, criteria);
    }
}
