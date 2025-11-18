package com.lastminute.recruitment.domain;

import com.lastminute.recruitment.client.WikiParser;
import com.lastminute.recruitment.persistence.WikiPageRepository;

import java.io.IOException;

public record WikiScrapper(WikiParser parser, WikiPageRepository repository) {

    public void read(final String link) {
        try {
            parser.parse(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
