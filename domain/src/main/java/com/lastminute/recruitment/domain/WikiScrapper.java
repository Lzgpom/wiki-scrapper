package com.lastminute.recruitment.domain;

public class WikiScrapper {

    private final WikiClient client;

    public WikiScrapper(final WikiClient client) {
        this.client = client;
    }

    public void read(String link) {
        client.parse(link);
    }
}
