package com.lastminute.recruitment.client;

import com.lastminute.recruitment.domain.error.WikiPageNotFound;

import java.io.InputStream;

public class HtmlResourceWikiClient implements WikiClient {

    private static final String BASE_PATH = "wikiscrapper/";
    private static final String EXTENSION = ".html";

    private static final String BASE_LINK = "http://wikiscrapper.test/";

    @Override
    public InputStream loadLink(final String link) {
        final var resourcePath = BASE_PATH + link.replace(BASE_LINK, "") + EXTENSION;
        final var resourceStream = getClass().getClassLoader().getResourceAsStream(resourcePath);

        if (resourceStream == null) {
            throw new WikiPageNotFound();
        }

        return resourceStream;
    }
}
