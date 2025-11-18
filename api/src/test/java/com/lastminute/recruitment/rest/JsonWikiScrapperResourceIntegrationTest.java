package com.lastminute.recruitment.rest;

import com.lastminute.recruitment.domain.WikiScrapper;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WikiScrapperResource.class)
@ContextConfiguration(classes = {WikiScrapperResource.class, WikiScrapperControllerExceptionHandler.class})
class JsonWikiScrapperResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WikiScrapper wikiScrapper;

    @Nested
    class ScrapEndpoint {

        @Test
        void shouldScrapAllResources() throws Exception {
            // WHEN / THEN
            mockMvc.perform(post("/wiki/scrap")
                            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                            .content("\"http://wikiscrapper.test/site5\""))
                    .andExpect(status().isOk());
        }

        @Test
        void shouldReturnNotFoundForInvalidRootResource() throws Exception {
            // GIVEN
            doThrow(new WikiPageNotFound()).when(wikiScrapper).read(any());

            // WHEN / THEN
            mockMvc.perform(post("/wiki/scrap")
                            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                            .content("\"http://wikiscrapper.test/invalid\""))
                    .andExpect(status().isNotFound());
        }
    }
}
