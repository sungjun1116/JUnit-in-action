package com.example.junit.tdd;

import com.example.junit.ch2.Answer;
import com.example.junit.ch2.Criterion;

public class Profile {
    private Answer answer;

    public boolean matches(Criterion criterion) {
        return answer != null;
    }

    public void add(Answer answer) {
        this.answer = answer;
    }
    
}
