package com.example.junit.tdd;

import com.example.junit.ch2.Answer;
import com.example.junit.ch2.Bool;
import com.example.junit.ch2.BooleanQuestion;
import com.example.junit.ch2.Criteria;
import org.junit.jupiter.api.BeforeEach;

class ScoreTest {

    private Profile profile;
    private BooleanQuestion questionIsThereRelocation;
    private BooleanQuestion questionReimburseTuition;
    private Answer answerThereIsRelocation;
    private Answer answerThereIsNotRelocation;
    private Answer answerReimburseTuition;
    private Answer answerDoesNotReimburseTuition;
    private Criteria criteria;

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

    @BeforeEach
    public void createCriteria() {
        criteria = new Criteria();
    }

    // @Test
    // void scoreIsZeroWhenThereAreNoMatches() {
    //     criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
    //
    //     ProfileMatch match = profile.match(criteria);
    //
    //     Assertions.assertThat(match.getScore()).isZero();
    // }
}