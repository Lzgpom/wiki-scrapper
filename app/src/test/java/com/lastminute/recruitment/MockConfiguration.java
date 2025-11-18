package com.lastminute.recruitment;

import com.lastminute.recruitment.persistence.MockWikiPageRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class MockConfiguration {

    @Primary
    @Bean("repository.stub")
    public MockWikiPageRepository wikiPageRepository() {
        return new MockWikiPageRepository();
    }
}
