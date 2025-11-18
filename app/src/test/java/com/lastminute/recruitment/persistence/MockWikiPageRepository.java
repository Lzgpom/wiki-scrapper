package com.lastminute.recruitment.persistence;

import com.lastminute.recruitment.domain.WikiPage;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MockWikiPageRepository implements WikiPageRepository {

    private final List<WikiPage> wikiPages;

    public MockWikiPageRepository() {
        this.wikiPages = new ArrayList<>();
    }

    @Override
    public void save(WikiPage wikiPage) {
        wikiPages.add(wikiPage);
    }

    public void assertSavedPagesCount(final int expectedCount) {
        assertThat(wikiPages).hasSize(expectedCount);
    }

    public void assertContainsPageWithLink(final String link) {
        assertThat(wikiPages).extracting(WikiPage::selfLink).contains(link);
    }
}
