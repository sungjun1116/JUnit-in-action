package com.example.junit.ch13;

import com.example.junit.ch13.domain.BooleanAnswer;
import com.example.junit.ch13.domain.BooleanQuestion;
import com.example.junit.ch13.domain.StatCompiler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatCompilerTest {

    @Mock
    private QuestionRepository controller;

    @InjectMocks
    private com.example.junit.ch13.domain.StatCompiler stats;

    @Test
    void responsesByQuestionAnswersCountsByQuestionText() {
        // given
        StatCompiler stats = new StatCompiler();
        List<BooleanAnswer> answers = new ArrayList<>();
        answers.add(new BooleanAnswer(1, true));
        answers.add(new BooleanAnswer(1, true));
        answers.add(new BooleanAnswer(1, true));
        answers.add(new BooleanAnswer(1, false));
        answers.add(new BooleanAnswer(2, true));
        answers.add(new BooleanAnswer(2, true));
        Map<Integer, String> questions = new HashMap<>();
        questions.put(1, "Tuition reimbursement?");
        questions.put(2, "Relocation package?");

        // when
        Map<String, Map<Boolean, AtomicInteger>> responses = stats.responsesByQuestion(answers, questions);

        // then
        assertThat(responses.get("Tuition reimbursement?").get(Boolean.TRUE).get()).isEqualTo(3);
        assertThat(responses.get("Tuition reimbursement?").get(Boolean.FALSE).get()).isEqualTo(1);
        assertThat(responses.get("Relocation package?").get(Boolean.TRUE).get()).isEqualTo(2);
        assertThat(responses.get("Relocation package?").get(Boolean.FALSE).get()).isZero();
    }

    @Test
    void questionTextDoesStuff() {
        when(controller.find(1)).thenReturn(new BooleanQuestion("text1"));
        when(controller.find(2)).thenReturn(new BooleanQuestion("text2"));
        List<BooleanAnswer> answers = new ArrayList<>();
        answers.add(new BooleanAnswer(1, true));
        answers.add(new BooleanAnswer(2, true));

        Map<Integer, String> questionText = stats.questionText(answers);

        Map<Integer, String> expected = new HashMap<>();
        expected.put(1, "text1");
        expected.put(2, "text2");
        assertThat(questionText).isEqualTo(expected);
    }

}