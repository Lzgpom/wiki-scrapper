package com.lastminute.recruitment.client;

import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


class JsonResourceWikiClientTest {

    @Nested
    class LoadLink {

        @Test
        void shouldLoadExistingResource() {
            // GIVEN
            final var systemUnderTest = new JsonResourceWikiClient();
            final var link = "http://wikiscrapper.test/site1";

            // WHEN
            final var result = systemUnderTest.loadLink(link);

            // THEN
            assertThat(result).isNotNull();
        }

        @Test
        void shouldThrowExceptionWhenResourceNotFound() {
            // GIVEN
            final var systemUnderTest = new JsonResourceWikiClient();
            final var link = "wikiscrapper/nonexistent.json";

            // WHEN / THEN
            assertThatExceptionOfType(WikiPageNotFound.class).isThrownBy(() -> systemUnderTest.loadLink(link));
        }
    }

}