package com.lastminute.recruitment.persistence;

import com.lastminute.recruitment.domain.WikiPage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WikiPageRepositoryImpl implements WikiPageRepository {

    private static final Logger log = Logger.getLogger(WikiPageRepositoryImpl.class.getName());

    public void save(WikiPage wikiPage) {
        log.log(Level.INFO, "saving wiki page {0}", wikiPage);
    }
}
