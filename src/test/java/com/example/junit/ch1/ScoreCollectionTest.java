package com.example.junit.ch1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ScoreCollectionTest {

    @Test
    void answersArithmeticMeanOfTwoNumbers() {
        // arrange
        ScoreCollection collection = new ScoreCollection();
        collection.add(() -> 5);
        collection.add(() -> 7);

        // act
        int actualResult = collection.arithmeticMean();

        // assert
        Assertions.assertThat(actualResult).isEqualTo(6);
    }
}