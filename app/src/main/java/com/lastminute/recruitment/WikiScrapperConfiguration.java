package com.lastminute.recruitment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.recruitment.client.HtmlResourceWikiClient;
import com.lastminute.recruitment.client.HtmlWikiParser;
import com.lastminute.recruitment.client.JsonResourceWikiClient;
import com.lastminute.recruitment.client.JsonWikiParser;
import com.lastminute.recruitment.client.WikiClient;
import com.lastminute.recruitment.client.WikiParser;
import com.lastminute.recruitment.domain.WikiScrapper;
import com.lastminute.recruitment.persistence.WikiPageRepository;
import com.lastminute.recruitment.persistence.WikiPageRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class WikiScrapperConfiguration {

    private static final String JSON_PROFILE = "json";
    private static final String HTML_PROFILE = "html";

    @Bean
    @Profile(JSON_PROFILE)
    public WikiClient jsonResourceWikiClient() {
        return new JsonResourceWikiClient();
    }

    @Bean
    @Profile(JSON_PROFILE)
    public WikiParser jsonWikiReader(final ObjectMapper mapper) {
        return new JsonWikiParser(mapper);
    }

    @Bean
    @Profile(HTML_PROFILE)
    public WikiClient htmlResourceWikiClient() {
        return new HtmlResourceWikiClient();
    }

    @Bean
    @Profile(HTML_PROFILE)
    public WikiParser htmlWikiReader() {
        return new HtmlWikiParser();
    }

    @Bean
    public WikiPageRepository wikiPageRepository() {
        return new WikiPageRepositoryImpl();
    }

    @Bean
    public WikiScrapper wikiScrapper(final WikiClient client, final WikiParser parser, final WikiPageRepository repository) {
        return new WikiScrapper(client, parser, repository);
    }
}
