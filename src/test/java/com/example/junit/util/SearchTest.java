package com.example.junit.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

import static com.example.junit.util.ContainsMatches.containsMatches;
import static org.hamcrest.MatcherAssert.assertThat;

class SearchTest {
    private static final String A_TITLE = "1";

    @Test
    void testSearch() throws IOException {
        InputStream stream = streamOn("There are certain queer times and occasions "
                + "in this strange mixed affair we call life when a man "
                + "takes this whole universe for a vast practical joke, "
                + "though the wit thereof he but dimly discerns, and more "
                + "than suspects that the joke is at nobody's expense but "
                + "his own.");
        Search search = new Search(stream, "practical joke", A_TITLE);
        Search.LOGGER.setLevel(Level.OFF);
        search.setSurroundingCharacterCount(10);
        search.execute();
        Assertions.assertFalse(search.errored());
        assertThat(search.getMatches(), containsMatches(new Match[]{
                new Match(A_TITLE,
                        "practical joke",
                        "or a vast practical joke, though t")
        }));
        stream.close();
    }

    @Test
    void noMatchesReturnedWhenSearchStringNotInContent() throws IOException {
        URLConnection connection = new URL("http://bit.ly/15sYPA7").openConnection();
        InputStream inputStream = connection.getInputStream();
        Search search = new Search(inputStream, "smelt", A_TITLE);
        search.execute();
        Assertions.assertTrue(search.getMatches().isEmpty());
        inputStream.close();
    }

    private InputStream streamOn(String pageContent) {
        return new ByteArrayInputStream(pageContent.getBytes());
    }
}