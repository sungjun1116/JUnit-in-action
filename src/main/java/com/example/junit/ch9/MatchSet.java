package com.example.junit.ch9;

import com.example.junit.ch2.Answer;
import com.example.junit.ch2.Criteria;
import com.example.junit.ch2.Criterion;
import com.example.junit.ch2.Weight;
import lombok.Getter;

import java.util.Map;

public class MatchSet {
    private Map<String, Answer> answers;
    @Getter
    private int score = 0;
    private Criteria criteria;

    public MatchSet(Map<String, Answer> answers, Criteria criteria) {
        this.answers = answers;
        this.criteria = criteria;
        calculateScore();
    }

    public void calculateScore() {
        for (Criterion criterion : criteria) {
            if (criterion.matches(answerMatching(criterion))) {
                score += criterion.getWeight().getValue();
            }
        }
    }
    public boolean matches() {
        if (doesNotMeetAnyMustMatchCriterion()){
            return false;
        }
        return anyMatches();
    }

    private boolean doesNotMeetAnyMustMatchCriterion() {
        for (Criterion criterion : criteria) {
            boolean match = criterion.matches(answerMatching(criterion));
            if (!match && criterion.getWeight() == Weight.MustMatch) {
                return true;
            }
        }
        return false;
    }

    private boolean anyMatches() {
        boolean anyMatches = false;
        for (Criterion criterion : criteria) {
            anyMatches |= criterion.matches(answerMatching(criterion));
        }
        return anyMatches;
    }

    private Answer answerMatching(Criterion criterion) {
        return answers.get(criterion.getAnswer().getQuestionText());
    }
}
