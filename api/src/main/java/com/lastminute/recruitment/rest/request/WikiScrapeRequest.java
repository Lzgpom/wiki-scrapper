package com.lastminute.recruitment.rest.request;

import com.fasterxml.jackson.annotation.JsonValue;

public record WikiScrapeRequest(@JsonValue String link) {
}
