package com.example.junit.tdd;

import com.example.junit.ch2.Answer;
import com.example.junit.ch2.Criterion;

import java.util.HashMap;
import java.util.Map;

public class AnswerCollection {

    private Map<String, Answer> answers = new HashMap<>();

    public void add(Answer answer) {
        answers.put(answer.getQuestionText(), answer);
    }

    public int size() {
        return answers.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Answer getMatchingAnswer(Criterion criterion) {
        return answers.get(criterion.getAnswer().getQuestionText());
    }
}
