package com.lastminute.recruitment.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.recruitment.domain.WikiPage;

import java.io.IOException;
import java.io.InputStream;

public record JsonWikiParser(ObjectMapper mapper) implements WikiParser {

    @Override
    public WikiPage parse(final InputStream source) throws IOException {
        return mapper.readValue(source, WikiPage.class);
    }
}
