package com.example.junit.ch5;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BooleanAnswer {
    private int questionId;
    private boolean value;
}