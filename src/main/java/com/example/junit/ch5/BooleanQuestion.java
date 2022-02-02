package com.example.junit.ch5;

import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
public class BooleanQuestion extends Question {
    private static final long serialVersionUID = 1L;

    public BooleanQuestion(String text) {
        super(text);
    }

    @Override
    public List<String> getAnswerChoices() {
        return Arrays.asList(new String[] { "No", "Yes" });
    }

    @Override
    public boolean match(int expected, int actual) {
        return expected == actual;
    }
}
