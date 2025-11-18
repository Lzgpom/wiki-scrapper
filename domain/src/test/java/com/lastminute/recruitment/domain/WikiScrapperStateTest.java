package com.lastminute.recruitment.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WikiScrapperStateTest {

    @Nested
    class AddLinksToProcess {

        @Test
        void shouldAddUniqueLinks() {
            // GIVEN
            final var systemUnderTest = new WikiScrapperState();
            final var links = List.of("link1", "link2", "link1", "link3");

            // WHEN
            systemUnderTest.addLinksToProcess(links);

            // THEN
            final var extractedLinks = extractAllLinks(systemUnderTest);
            assertThat(extractedLinks).containsExactlyInAnyOrder("link1", "link2", "link3");
        }

        @Test
        void shouldNotAddAlreadyProcessedLinks() {
            // GIVEN
            final var systemUnderTest = new WikiScrapperState();
            systemUnderTest.addLinkToProcess("link1");
            systemUnderTest.addLinkToProcess("link2");
            extractAllLinks(systemUnderTest);

            final var links = List.of("link2", "link3", "link1", "link4");

            // WHEN
            systemUnderTest.addLinksToProcess(links);

            // THEN
            final var extractedLinks = extractAllLinks(systemUnderTest);
            assertThat(extractedLinks).containsExactlyInAnyOrder( "link3", "link4");
        }
    }

    @Nested
    class AddLinkToProcess {

        @Test
        void shouldAddSingleUniqueLink() {
            // GIVEN
            final var systemUnderTest = new WikiScrapperState();

            // WHEN
            systemUnderTest.addLinkToProcess("link1");

            // THEN
            final var extractedLinks = extractAllLinks(systemUnderTest);
            assertThat(extractedLinks).containsExactly("link1");
        }

        @Test
        void shouldNotAddAlreadyProcessedLink() {
            // GIVEN
            final var systemUnderTest = new WikiScrapperState();
            systemUnderTest.addLinkToProcess("link1");
            extractAllLinks(systemUnderTest);

            // WHEN
            systemUnderTest.addLinkToProcess("link1");

            // THEN
            final var extractedLinks = extractAllLinks(systemUnderTest);
            assertThat(extractedLinks).isEmpty();
        }
    }

    @Nested
    class PollLink {

        @Test
        void shouldPollLinks() {
            // GIVEN
            final var systemUnderTest = new WikiScrapperState();
            systemUnderTest.addLinkToProcess("link1");
            systemUnderTest.addLinkToProcess("link2");
            systemUnderTest.addLinkToProcess("link3");

            // WHEN
            final var polledLinks = new ArrayList<String>();
            while (systemUnderTest.hasLinksToProcess()) {
                polledLinks.add(systemUnderTest.pollLink());
            }

            // THEN
            assertThat(polledLinks).containsExactlyInAnyOrder("link1", "link2", "link3");
        }
    }

    @Nested
    class HasLinksToProcess {

        @Test
        void shouldReturnTrueWhenLinksAreAvailable() {
            // GIVEN
            final var systemUnderTest = new WikiScrapperState();
            systemUnderTest.addLinkToProcess("link1");

            // WHEN
            final var hasLinks = systemUnderTest.hasLinksToProcess();

            // THEN
            assertThat(hasLinks).isTrue();
        }

        @Test
        void shouldReturnFalseWhenNoLinksAreAvailable() {
            // GIVEN
            final var systemUnderTest = new WikiScrapperState();

            // WHEN
            final var hasLinks = systemUnderTest.hasLinksToProcess();

            // THEN
            assertThat(hasLinks).isFalse();
        }
    }

    private static List<String> extractAllLinks(final WikiScrapperState systemUnderTest) {
        final var extractedLinks = new ArrayList<String>();
        while (systemUnderTest.hasLinksToProcess()) {
            extractedLinks.add(systemUnderTest.pollLink());
        }

        return extractedLinks;
    }
}