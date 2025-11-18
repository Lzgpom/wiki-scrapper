package com.lastminute.recruitment.client;

import com.lastminute.recruitment.domain.WikiPage;

import java.io.IOException;
import java.io.InputStream;

public interface WikiParser {
    WikiPage parse(InputStream source) throws IOException;
}
