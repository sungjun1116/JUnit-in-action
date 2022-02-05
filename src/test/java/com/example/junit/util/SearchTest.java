package com.example.junit.util;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import static com.example.junit.util.ContainsMatches.containsMatches;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchTest {
    private static final String A_TITLE = "1";
    private InputStream stream;

    @BeforeEach
    public void turnOffLogging() {
        Search.LOGGER.setLevel(Level.OFF);
    }

    @AfterEach
    public void closeResources() throws IOException {
        stream.close();
    }

    @Test
    void testSearch() {
        // given
        stream = streamOn("rest of text here"
                + "1234567890search term1234567890"
                + "more rest of text");
        Search search = new Search(stream, "search term", A_TITLE);
        Search.LOGGER.setLevel(Level.OFF);
        search.setSurroundingCharacterCount(10);

        // when
        search.execute();

        // then
        assertFalse(search.errored());
        assertThat(search.getMatches(), containsMatches(new Match[]{
                new Match(A_TITLE,
                        "search term",
                        "1234567890search term1234567890")
        }));
    }

    @Test
    void noMatchesReturnedWhenSearchStringNotInContent() {
        // given
        stream = streamOn("any text");
        Search search = new Search(stream, "text that doesn't match", A_TITLE);

        // when
        search.execute();

        // then
        assertTrue(search.getMatches().isEmpty());
    }

    @Test
    void returnsErroredWhenUnableToReadStream() {
        // given
        stream = createStreamThrowingErrorWhenRead();
        Search search = new Search(stream, "", "");

        // when
        search.execute();

        // then
        assertTrue(search.errored());
    }

    @Test
    void erroredReturnsFalseWhenReadSucceeds() {
        // given
        stream = streamOn("");
        Search search = new Search(stream, "", "");

        // when
        search.execute();

        // then
        assertFalse(search.errored());
    }

    private InputStream streamOn(String pageContent) {
        return new ByteArrayInputStream(pageContent.getBytes());
    }

    private InputStream createStreamThrowingErrorWhenRead() {
        return new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException();
            }
        };
    }
}