package com.lastminute.recruitment;

import com.lastminute.recruitment.client.HtmlWikiClient;
import com.lastminute.recruitment.client.JsonWikiClient;
import com.lastminute.recruitment.domain.WikiClient;
import com.lastminute.recruitment.domain.WikiScrapper;
import com.lastminute.recruitment.persistence.WikiPageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class WikiScrapperConfiguration {

    @Bean
    @Profile("json")
    public WikiClient jsonWikiReader() {
        return new JsonWikiClient();
    }

    @Bean
    @Profile("html")
    public WikiClient htmlWikiReader() {
        return new HtmlWikiClient();
    }

    @Bean
    public WikiPageRepository wikiPageRepository() {
        return new WikiPageRepository();
    }

    @Bean
    public WikiScrapper wikiScrapper(final WikiClient client) {
        return new WikiScrapper(client);
    }
}
