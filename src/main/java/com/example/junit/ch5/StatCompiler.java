package com.example.junit.ch5;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class StatCompiler {
    static Question q1 = new BooleanQuestion("Tuition reimbursement?");
    static Question q2 = new BooleanQuestion("Relocation package?");

    static class QuestionController {
        Question find(int id) {
            if (id == 1)
                return q1;
            else
                return q2;
        }
    }

    private QuestionController controller = new QuestionController();

    public Map<Integer, String> questionText(List<BooleanAnswer> answers) {
        Map<Integer, String> questions = new HashMap<>();
        answers.forEach(answer -> {
            if (!questions.containsKey(answer.getQuestionId())) {
                questions.put(answer.getQuestionId(), controller.find(answer.getQuestionId()).getText());
            }
        });
        return questions;
    }

    public Map<String, Map<Boolean, AtomicInteger>> responsesByQuestion(List<BooleanAnswer> answers, Map<Integer, String> questions) {
        Map<Integer, Map<Boolean, AtomicInteger>> responses = new HashMap<>();
        answers.forEach(answer -> incrementHistogram(responses, answer));
        return convertHistogramIdsToText(responses, questions);
    }

    private Map<String, Map<Boolean, AtomicInteger>> convertHistogramIdsToText(Map<Integer, Map<Boolean, AtomicInteger>> responses, Map<Integer, String> questions) {
        Map<String, Map<Boolean, AtomicInteger>> textResponses = new HashMap<>();
        responses.keySet().forEach(id -> textResponses.put(questions.get(id), responses.get(id)));
        return textResponses;
    }

    private void incrementHistogram(Map<Integer, Map<Boolean, AtomicInteger>> responses, BooleanAnswer answer) {
        Map<Boolean, AtomicInteger> histogram = getHistogram(responses, answer.getQuestionId());
        histogram.get(answer.isValue()).getAndIncrement();
    }


    private Map<Boolean, AtomicInteger> getHistogram(Map<Integer, Map<Boolean, AtomicInteger>> responses, int id) {
        Map<Boolean, AtomicInteger> histogram = null;
        if (responses.containsKey(id)) {
            histogram = responses.get(id);
            return histogram;
        }
        histogram = createNewHistogram();
        responses.put(id, histogram);
        return histogram;
    }

    private Map<Boolean, AtomicInteger> createNewHistogram() {
        Map<Boolean, AtomicInteger> histogram = new HashMap<>();
        histogram.put(Boolean.FALSE, new AtomicInteger(0));
        histogram.put(Boolean.TRUE, new AtomicInteger(0));
        return histogram;
    }
}
