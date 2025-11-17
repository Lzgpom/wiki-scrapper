package com.lastminute.recruitment.rest;

import com.lastminute.recruitment.domain.WikiScrapper;
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

    @PostMapping("/scrap")
    public void scrapWikipedia(@RequestBody String link) {
        scrapper.read(link);
    }

}
