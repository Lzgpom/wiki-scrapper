package com.lastminute.recruitment.domain;

import com.lastminute.recruitment.client.WikiClient;
import com.lastminute.recruitment.client.WikiParser;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import com.lastminute.recruitment.persistence.WikiPageRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WikiScrapperTest {

    @InjectMocks
    private WikiScrapper systemUnderTest;

    @Mock
    WikiClient client;

    @Mock
    WikiParser parser;

    @Spy
    WikiPageRepositoryMock repository = new WikiPageRepositoryMock();

    @Nested
    class Read {

        private final WikiPage rootPage = new WikiPage("Root Title", "root Content", "root", List.of("child1", "child2"));
        private final WikiPage child1Page = new WikiPage("Child 1 Title", "Child 1 Content", "child1", List.of("root", "child2"));
        private final WikiPage child2Page = new WikiPage("Child 2 Title", "Child 2 Content", "child2", List.of("child1"));

        @Test
        void shouldNotSaveAnyPageWhenParserThrowsIOException() throws IOException {
            // GIVEN
            final var link = "link";
            when(parser.parse(any())).thenThrow(new IOException());

            // WHEN
            systemUnderTest.read(link);

            // THEN
            assertThat(repository.getSavedPages()).isEmpty();
        }

        @Test
        void shouldThrowWikiPageNotFoundExceptionWhenClientCannotLoadRootLink() {
            // GIVEN
            final var link = "invalidLink";
            when(client.loadLink(link)).thenThrow(new WikiPageNotFound());

            // WHEN / THEN
            assertThatExceptionOfType(WikiPageNotFound.class).isThrownBy(() -> systemUnderTest.read(link));
        }

        @Test
        void shouldNotThrowWikiPageNotFoundExceptionWhenClientCannotLoadChildLink() throws IOException {
            // GIVEN
            final var rootLink = "root";

            when(client.loadLink(rootLink)).thenReturn(null);
            when(client.loadLink("child1")).thenReturn(null);
            when(client.loadLink("child2")).thenThrow(new WikiPageNotFound());
            when(parser.parse(any())).thenReturn(rootPage);

            // WHEN / THEN
            assertThatNoException().isThrownBy(() -> systemUnderTest.read(rootLink));

            // AND THEN
            final var savedPages = repository.getSavedPages();
            assertThat(savedPages).hasSize(2);
        }

        @Test
        void shouldSaveAllNestedPagesExactlyOnce() throws IOException {
            // GIVEN
            final var link = "root";
            mockPage("root", rootPage);
            mockPage("child1", child1Page);
            mockPage("child2", child2Page);

            // WHEN
            systemUnderTest.read(link);

            // THEN
            final var savedPages = repository.getSavedPages();
            assertThat(savedPages).hasSize(3);
            assertThat(savedPages).extracting(WikiPage::selfLink).containsExactlyInAnyOrder("root", "child1", "child2");
        }
    }

    private void mockPage(final String link, final WikiPage page) throws IOException {
        final var inputStream = mock(InputStream.class);
        when(client.loadLink(link)).thenReturn(inputStream);
        when(parser.parse(eq(inputStream))).thenReturn(page);
    }

    private static class WikiPageRepositoryMock implements WikiPageRepository {

        private final List<WikiPage> savedPages = new ArrayList<>();

        @Override
        public void save(WikiPage page) {
            savedPages.add(page);
        }

        public List<WikiPage> getSavedPages() {
            return savedPages;
        }
    }
}