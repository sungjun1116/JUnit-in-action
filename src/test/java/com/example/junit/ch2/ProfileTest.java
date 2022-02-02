package com.example.junit.ch2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProfileTest {

    private Profile profile;
    private Criteria criteria;
    private Answer answerThereIsRelocation;
    private Answer answerThereIsNotRelocation;
    private Answer answerDoseNotReimburseTuition;
    private Answer answerReimburseTuition;
    private BooleanQuestion questionIsThereRelocation;
    private BooleanQuestion questionReimburseTuition;

    @BeforeEach
    public void setup() {
        profile = new Profile();
        criteria = new Criteria();
        questionIsThereRelocation = new BooleanQuestion(1, "Relocation package?");
        questionReimburseTuition = new BooleanQuestion(1, "Reimburse tuition?");
        answerThereIsRelocation = new Answer(questionIsThereRelocation, Bool.TRUE);
        answerThereIsNotRelocation = new Answer(questionIsThereRelocation, Bool.FALSE);
        answerReimburseTuition = new Answer(questionReimburseTuition, Bool.TRUE);
        answerDoseNotReimburseTuition = new Answer(questionReimburseTuition, Bool.FALSE);
    }

    @Test
    void matchAnswersFalseWhenCriteriaIsEmpty() {
        // given
        profile.add(answerThereIsNotRelocation);

        // when
        boolean matches = profile.matches(criteria);

        // then
        assertFalse(matches);
    }

    @Test
    void matchAnswersFalseWhenMustMatchCriteriaNotMet() {
        // given
        profile.add(answerThereIsNotRelocation);
        criteria.add(new Criterion(new Answer(questionIsThereRelocation, Bool.TRUE), Weight.MustMatch));

        // when
        boolean matches = profile.matches(criteria);

        // then
        assertFalse(matches);
    }

    @Test
    void matchAnswersTrueForAnyDontCareCriteria() {
        // given
        profile.add(answerThereIsNotRelocation);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.DontCare));

        // when
        boolean matches = profile.matches(criteria);

        // then
        assertTrue(matches);
    }

    @Test
    void matchesWhenProfileContainsMatchingAnswer() {
        // given
        profile.add(answerThereIsRelocation);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));

        // when
        boolean result = profile.matches(criteria);

        // then
        assertTrue(result);
    }

    @Test
    void doesNotMatchWhenNoMatchingAnswer() {
        // given
        profile.add(answerThereIsNotRelocation);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));

        // when
        boolean result = profile.matches(criteria);

        // then
        assertFalse(result);
    }

    @Test
    void matchAnswersTrueWhenAnyOfMultipleCriteriaMatch() {
        // given
        profile.add(answerThereIsRelocation);
        profile.add(answerDoseNotReimburseTuition);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
        criteria.add(new Criterion(answerReimburseTuition, Weight.Important));

        // when
        boolean matches = profile.matches(criteria);

        // then
        assertTrue(matches);
    }

}

