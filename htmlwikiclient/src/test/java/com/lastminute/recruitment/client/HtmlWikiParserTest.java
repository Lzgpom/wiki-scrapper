package com.lastminute.recruitment.client;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HtmlWikiParserTest {

    private final HtmlWikiParser systemUnderTest = new HtmlWikiParser();

    @Nested
    class Parse {

        @Test
        void shouldParseHtmlWikiPage() throws Exception {
            // GIVEN
            final var source = getClass().getClassLoader().getResourceAsStream("wikiscrapper/site5.html");

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
    }

}