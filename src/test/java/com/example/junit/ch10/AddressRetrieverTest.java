package com.example.junit.ch10;

import com.example.junit.ch10.util.Http;
import org.assertj.core.api.Assertions;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;

class AddressRetrieverTest {

    @Mock
    private Http http;

    @InjectMocks
    private AddressRetriever retriever;

    @BeforeEach
    public void createRetriever() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void answersAppropriateAddressForValidCoordinates()
            throws IOException, ParseException {
        when(http.get(contains("lat=38.000000&lon=-104.000000")))
                .thenReturn("{\"address\":{"
                        + "\"house_number\":\"324\","
                        + "\"road\":\"North Tejon Street\","
                        + "\"city\":\"Colorado Springs\","
                        + "\"state\":\"Colorado\","
                        + "\"postcode\":\"80903\","
                        + "\"country_code\":\"us\"}"
                        + "}");

        Address address = retriever.retrieve(38.0, -104.0);

        Assertions.assertThat(address.houseNumber).isEqualTo("324");
        Assertions.assertThat(address.road).isEqualTo("North Tejon Street");
        Assertions.assertThat(address.city).isEqualTo("Colorado Springs");
        Assertions.assertThat(address.state).isEqualTo("Colorado");
        Assertions.assertThat(address.zip).isEqualTo("80903");
    }

}