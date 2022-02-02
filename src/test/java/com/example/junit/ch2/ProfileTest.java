package com.example.junit.ch2;

import com.example.junit.ch9.AnswerCollection;
import com.example.junit.ch9.MatchSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProfileTest {

    private Criteria criteria;
    private AnswerCollection answers;
    private Answer answerThereIsRelocation;
    private Answer answerThereIsNotRelocation;
    private Answer answerDoseNotReimburseTuition;
    private Answer answerReimburseTuition;
    private BooleanQuestion questionIsThereRelocation;
    private BooleanQuestion questionReimburseTuition;

    @BeforeEach
    public void setup() {
        criteria = new Criteria();
        answers = new AnswerCollection();
        questionIsThereRelocation = new BooleanQuestion(1, "Relocation package?");
        questionReimburseTuition = new BooleanQuestion(1, "Reimburse tuition?");
        answerThereIsRelocation = new Answer(questionIsThereRelocation, Bool.TRUE);
        answerThereIsNotRelocation = new Answer(questionIsThereRelocation, Bool.FALSE);
        answerReimburseTuition = new Answer(questionReimburseTuition, Bool.TRUE);
        answerDoseNotReimburseTuition = new Answer(questionReimburseTuition, Bool.FALSE);
    }

    private void add(Answer answer) {
        answers.add(answer);
    }

    private MatchSet createMatchSet() {
        return new MatchSet(answers, criteria);
    }

    @Test
    void matchAnswersFalseWhenCriteriaIsEmpty() {
        // given
        add(answerThereIsNotRelocation);

        // when
        boolean matches = createMatchSet().matches();

        // then
        assertFalse(matches);
    }

    @Test
    void matchAnswersFalseWhenMustMatchCriteriaNotMet() {
        // given
        add(answerThereIsNotRelocation);
        criteria.add(new Criterion(new Answer(questionIsThereRelocation, Bool.TRUE), Weight.MustMatch));

        // when
        boolean matches = createMatchSet().matches();

        // then
        assertFalse(matches);
    }

    @Test
    void matchAnswersTrueForAnyDontCareCriteria() {
        // given
        add(answerThereIsNotRelocation);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.DontCare));

        // when
        boolean matches = createMatchSet().matches();

        // then
        assertTrue(matches);
    }

    @Test
    void matchesWhenProfileContainsMatchingAnswer() {
        // given
        add(answerThereIsRelocation);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));

        // when
        boolean result = createMatchSet().matches();

        // then
        assertTrue(result);
    }

    @Test
    void doesNotMatchWhenNoMatchingAnswer() {
        // given
        add(answerThereIsNotRelocation);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));

        // when
        boolean result = createMatchSet().matches();

        // then
        assertFalse(result);
    }

    @Test
    void matchAnswersTrueWhenAnyOfMultipleCriteriaMatch() {
        // given
        add(answerThereIsRelocation);
        add(answerDoseNotReimburseTuition);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
        criteria.add(new Criterion(answerReimburseTuition, Weight.Important));

        // when
        boolean matches = createMatchSet().matches();

        // then
        assertTrue(matches);
    }
}

