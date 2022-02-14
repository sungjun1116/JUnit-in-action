package com.example.junit.ch13;

import com.example.junit.ch2.Answer;
import com.example.junit.ch2.Bool;
import com.example.junit.ch2.BooleanQuestion;
import com.example.junit.ch2.Criteria;
import com.example.junit.ch2.Criterion;
import com.example.junit.ch2.Weight;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class ProfileMatcherTest {
    private BooleanQuestion question;
    private Criteria criteria;
    private ProfileMatcher matcher;
    private Profile matchingProfile;
    private Profile nonMatchingProfile;
    private MatchListener listener;

    @BeforeEach
    void create() {
        question = new BooleanQuestion(1, "");
        criteria = new Criteria();
        criteria.add(new Criterion(matchingAnswer(), Weight.MustMatch));
        matchingProfile = createMatchingProfile("matching");
        nonMatchingProfile = createNonMatchingProfile("nonMatching");
    }

    @BeforeEach
    void createMatchListener() {
        listener = mock(MatchListener.class);
    }

    private Profile createMatchingProfile(String name) {
        Profile profile = new Profile(name);
        profile.add(matchingAnswer());
        return profile;
    }

    private Profile createNonMatchingProfile(String name) {
        Profile profile = new Profile(name);
        profile.add(nonMatchingAnswer());
        return profile;
    }

    @BeforeEach
    void createMatcher() {
        matcher = new ProfileMatcher();
    }

    @Test
    void collectsMatchSets() {
        matcher.add(matchingProfile);
        matcher.add(nonMatchingProfile);
        List<MatchSet> sets = matcher.collectMatchSets(criteria);

        Assertions.assertThat(sets.stream().map(MatchSet::getProfileId).collect(Collectors.toSet()))
                .isEqualTo(new HashSet<>(Arrays.asList(matchingProfile.getId(), nonMatchingProfile.getId())));
    }

    @Test
    void processNotifiesListenerOnMatch() {
        matcher.add(matchingProfile);
        MatchSet set = matchingProfile.getMatchSet(criteria);

        matcher.process(listener, set);

        verify(listener).foundMatch(matchingProfile, set);
    }

    @Test
    void processDoesNotNotifiesListenerOnMatch() {
        matcher.add(nonMatchingProfile);
        MatchSet set = nonMatchingProfile.getMatchSet(criteria);

        matcher.process(listener, set);

        verify(listener, never()).foundMatch(nonMatchingProfile, set);
    }


    private Answer matchingAnswer() {
        return new Answer(question, Bool.TRUE);
    }

    private Answer nonMatchingAnswer() {
        return new Answer(question, Bool.FALSE);
    }
}