package com.lastminute.recruitment.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIOException;

class JsonWikiParserTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private final JsonWikiParser systemUnderTest = new JsonWikiParser(mapper);

    @Nested
    class Parse {

        @Test
        void shouldParseJsonWikiPage() throws Exception {
            // GIVEN
            final var source = getClass().getClassLoader().getResourceAsStream("wikiscrapper/site5.json");

            // WHEN
            final var result = systemUnderTest.parse(source);

            // THEN
            assertThat(result.title()).isEqualTo("Site 5");
            assertThat(result.content()).isEqualTo("Content 5");
            assertThat(result.selfLink()).isEqualTo("http://wikiscrapper.test/site5");
            assertThat(result.links()).contains(
                    "http://wikiscrapper.test/site1",
                    "http://wikiscrapper.test/site2",
                    "http://wikiscrapper.test/site3",
                    "http://wikiscrapper.test/site4"
            );
        }

       @Test
       void shouldThrowExceptionWhenJsonIsInvalid() {
           // GIVEN
           final var json = "invalid json";
           final var source = new ByteArrayInputStream(json.getBytes());

           // WHEN / THEN
           assertThatIOException().isThrownBy(() -> systemUnderTest.parse(source));
       }
    }
}