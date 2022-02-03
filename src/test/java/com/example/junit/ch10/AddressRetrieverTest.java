package com.example.junit.ch10;

import com.example.junit.ch10.util.Http;
import org.assertj.core.api.Assertions;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class AddressRetrieverTest {

    @Test
    void answersAppropriateAddressForValidCoordinates()
            throws IOException, ParseException {
        Http http = (String url) ->
                "{\"address\":{"
                        + "\"house_number\":\"324\","
                        + "\"road\":\"North Tejon Street\","
                        + "\"city\":\"Colorado Springs\","
                        + "\"state\":\"Colorado\","
                        + "\"postcode\":\"80903\","
                        + "\"country_code\":\"us\"}"
                        + "}";
        AddressRetriever retriever = new AddressRetriever(http);

        Address address = retriever.retrieve(38.0, -104.0);

        Assertions.assertThat(address.houseNumber).isEqualTo("324");
        Assertions.assertThat(address.road).isEqualTo("North Tejon Street");
        Assertions.assertThat(address.city).isEqualTo("Colorado Springs");
        Assertions.assertThat(address.state).isEqualTo("Colorado");
        Assertions.assertThat(address.zip).isEqualTo("80903");
    }

}