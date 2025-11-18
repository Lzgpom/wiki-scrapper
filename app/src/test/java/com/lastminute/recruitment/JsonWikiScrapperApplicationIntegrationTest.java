package com.lastminute.recruitment;

import com.lastminute.recruitment.persistence.MockWikiPageRepository;
import com.lastminute.recruitment.rest.WikiScrapperControllerExceptionHandler;
import com.lastminute.recruitment.rest.WikiScrapperResource;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("json")
@WebMvcTest(WikiScrapperResource.class)
@ContextConfiguration(classes = {
        WikiScrapperResource.class,
        WikiScrapperControllerExceptionHandler.class,
        WikiScrapperConfiguration.class,
        MockConfiguration.class
})
class JsonWikiScrapperApplicationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockWikiPageRepository wikiPageRepository;

    @Nested
    class ScrapEndpoint {

        @Test
        void shouldScrapAllResources() throws Exception {
            // WHEN
            mockMvc.perform(post("/wiki/scrap")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("\"http://wikiscrapper.test/site5\""))
                    .andExpect(status().isOk());

            // THEN
            wikiPageRepository.assertSavedPagesCount(5);
            wikiPageRepository.assertContainsPageWithLink("http://wikiscrapper.test/site1");
            wikiPageRepository.assertContainsPageWithLink("http://wikiscrapper.test/site2");
            wikiPageRepository.assertContainsPageWithLink("http://wikiscrapper.test/site3");
            wikiPageRepository.assertContainsPageWithLink("http://wikiscrapper.test/site4");
            wikiPageRepository.assertContainsPageWithLink("http://wikiscrapper.test/site5");
        }

        @Test
        void shouldReturnNotFoundForInvalidRootResource() throws Exception {
            // WHEN
            mockMvc.perform(post("/wiki/scrap")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("\"http://wikiscrapper.test/invalid\""))
                    .andExpect(status().isNotFound());

            // THEN
            wikiPageRepository.assertSavedPagesCount(0);
        }
    }
}