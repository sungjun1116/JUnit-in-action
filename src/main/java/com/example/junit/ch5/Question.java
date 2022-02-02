package com.example.junit.ch5;

import com.example.junit.ch2.Answer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public abstract class Question {
    private Integer id;
    private String text;
    private Instant instant;

    public Question(String text) {
        this.text = text;
    }

    abstract public List<String> getAnswerChoices();

    abstract public boolean match(int expected, int actual);

    public boolean match(Answer answer) {
        return false;
    }

    public String getAnswerChoice(int i) {
        return getAnswerChoices().get(i);
    }

    public int indexOf(String matchingAnswerChoice) {
        for (int i = 0; i < getAnswerChoices().size(); i++)
            if (getAnswerChoice(i).equals(matchingAnswerChoice))
                return i;
        return -1;
    }

}
