package com.example.junit.tdd;

import com.example.junit.ch2.Answer;
import com.example.junit.ch2.Bool;
import com.example.junit.ch2.BooleanQuestion;
import com.example.junit.ch2.Criterion;
import com.example.junit.ch2.Weight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Profile_MatchesCriterionTest {

    private Profile profile;
    private BooleanQuestion questionIsThereRelocation;
    private BooleanQuestion questionReimburseTuition;
    private Answer answerThereIsRelocation;
    private Answer answerThereIsNotRelocation;
    private Answer answerReimburseTuition;
    private Answer answerDoesNotReimburseTuition;

    @BeforeEach
    public void createProfile() {
        profile = new Profile();
    }

    @BeforeEach
    public void createQuestionAndAnswer() {
        questionIsThereRelocation = new BooleanQuestion(1, "Relocation package?");
        questionReimburseTuition = new BooleanQuestion(2, "Reimburse tuition?");
        answerThereIsRelocation = new Answer(questionIsThereRelocation, Bool.TRUE);
        answerThereIsNotRelocation = new Answer(questionIsThereRelocation, Bool.FALSE);
        answerReimburseTuition = new Answer(questionReimburseTuition, Bool.TRUE);
        answerDoesNotReimburseTuition = new Answer(questionReimburseTuition, Bool.FALSE);
    }

    @Test
    void trueWhenMatchesSoleAnswer() {
        Answer answer = new Answer(questionIsThereRelocation, Bool.TRUE);
        profile.add(answer);
        Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);

        assertTrue(profile.matches(criterion));
    }

    @Test
    void falseWhenNoMatchingAnswerContained() {
        profile.add(answerThereIsNotRelocation);
        Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);

        boolean result = profile.matches(criterion);

        assertFalse(result);
    }

    @Test
    void trueWhenOneOfMultipleAnswerMatches() {
        profile.add(answerThereIsRelocation);
        profile.add(answerDoesNotReimburseTuition);
        Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);

        boolean result = profile.matches(criterion);

        assertTrue(result);
    }

    @Test
    void trueForAnyDontCareCriterion() {
        profile.add(answerDoesNotReimburseTuition);
        Criterion criterion = new Criterion(answerReimburseTuition, Weight.DontCare);

        assertTrue(profile.matches(criterion));
    }
}
