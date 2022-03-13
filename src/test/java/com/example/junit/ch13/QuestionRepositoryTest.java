package com.example.junit.ch13;

import com.example.junit.ch13.domain.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

class QuestionRepositoryTest {

    private QuestionRepository repository;

    @BeforeEach
    public void create() {
        repository = new QuestionRepository();
        repository.deleteAll();
    }

    @AfterEach
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    void findsPersistedQuestionById() {
        int id = repository.addBooleanQuestion("question text");

        Question question = repository.find(id);

        assertThat(question.getText()).isEqualTo("question text");
    }

    @Test
    void questionAnswersDateAdded() {
        Instant now = new Date().toInstant();
        repository.setClock(Clock.fixed(now, ZoneId.of("America/Denver")));
        int id = repository.addBooleanQuestion("text");

        Question question = repository.find(id);

        assertThat(question.getCreateTimestamp()).isEqualTo(equalTo(now));
    }

    @Test
    void answersMultiplePersistedQuestions() {
        repository.addBooleanQuestion("q1");
        repository.addBooleanQuestion("q2");
        repository.addPercentileQuestion("q3", new String[]{"a1", "a2"});

        List<Question> questions = repository.getAll();

        assertThat(questions.stream()
                .map(Question::getText)
                .collect(Collectors.toList()))
                .isEqualTo(Arrays.asList("q1", "q2", "q3"));
    }

    @Test
    void findsMatchingEntries() {
        repository.addBooleanQuestion("alpha 1");
        repository.addBooleanQuestion("alpha 2");
        repository.addBooleanQuestion("beta 1");

        List<Question> questions = repository.findWithMatchingText("alpha");

        assertThat(questions.stream()
                .map(Question::getText)
                .collect(Collectors.toList()))
                .isEqualTo(Arrays.asList("alpha 1", "alpha 2"));
    }
}