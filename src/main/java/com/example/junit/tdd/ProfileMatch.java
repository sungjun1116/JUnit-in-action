package com.example.junit.tdd;

import com.example.junit.ch2.Criteria;
import com.example.junit.ch2.Criterion;

public class ProfileMatch {
    private AnswerCollection answers;

    private Criteria criteria;

    private int score;

    public ProfileMatch(AnswerCollection answers, Criteria criteria) {
        this.answers = answers;
        this.criteria = criteria;
    }

    public int getScore() {
        if (answers.isEmpty()) {
            return score;
        }
        for (Criterion criterion : criteria) {
            if (criterion.matches(answers.getMatchingAnswer(criterion))) {
                score += criterion.getWeight().getValue();
            }
        }
        return score;
    }

}
