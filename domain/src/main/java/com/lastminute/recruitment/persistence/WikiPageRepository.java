package com.lastminute.recruitment.persistence;

import com.lastminute.recruitment.domain.WikiPage;

public interface WikiPageRepository {
    void save(WikiPage wikiPage);
}
