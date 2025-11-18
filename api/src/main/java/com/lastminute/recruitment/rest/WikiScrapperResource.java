package com.lastminute.recruitment.rest;

import com.lastminute.recruitment.domain.WikiScrapper;
import com.lastminute.recruitment.rest.request.WikiScrapeRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/wiki")
@RestController
public class WikiScrapperResource {

    private final WikiScrapper scrapper;

    public WikiScrapperResource(final WikiScrapper scrapper) {
        this.scrapper = scrapper;
    }

    @PostMapping(value = "/scrap")
    public void scrapWikipedia(@RequestBody final WikiScrapeRequest request) {
        scrapper.read(request.link());
    }
}
