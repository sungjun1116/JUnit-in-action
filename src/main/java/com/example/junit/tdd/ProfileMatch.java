package com.example.junit.tdd;

import com.example.junit.ch2.Answer;
import com.example.junit.ch2.Criteria;
import com.example.junit.ch2.Criterion;

import java.util.Map;

public class ProfileMatch {
    private Map<String, Answer> answers;

    private Criteria criteria;

    private int score;

    public ProfileMatch(Map<String, Answer> answers, Criteria criteria) {
        this.answers = answers;
        this.criteria = criteria;
    }

    public int getScore() {
        if (answers.isEmpty()) {
            return score;
        }
        for (Criterion criterion : criteria) {
            if (criterion.matches(getMatchingProfileAnswer(criterion))) {
                score += criterion.getWeight().getValue();
            }
        }
        return score;
    }

    private Answer getMatchingProfileAnswer(Criterion criterion) {
        return answers.get(criterion.getAnswer().getQuestionText());
    }

}
