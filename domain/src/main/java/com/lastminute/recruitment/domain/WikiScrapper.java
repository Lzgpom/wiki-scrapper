package com.lastminute.recruitment.domain;

import com.lastminute.recruitment.client.WikiClient;
import com.lastminute.recruitment.client.WikiParser;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import com.lastminute.recruitment.persistence.WikiPageRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

public record WikiScrapper(WikiClient client, WikiParser parser, WikiPageRepository repository) {

    private static final Logger log = Logger.getLogger(WikiScrapper.class.getName());

    public void read(final String link) {
        validateLink(link);

        final var state = new WikiScrapperState();
        state.addLinkToProcess(link);

        while (state.hasLinksToProcess()) {
            final var currentLink = state.pollLink();
            try (final var inputStream = client.loadLink(currentLink)) {
                final var wikiPage = parser.parse(inputStream);
                state.addLinksToProcess(wikiPage.links());
                repository.save(wikiPage);
            } catch (final WikiPageNotFound e) {
                log.log(Level.WARNING, "Wiki page not found for link {0}", currentLink);
            } catch (final Exception e) {
                log.log(Level.WARNING, "Failed to process link {0}", currentLink);
                log.log(Level.FINE, "Failed to process link " + currentLink, e);
            }
        }
    }

    private void validateLink(final String link) {
        try (final var ignored = client.loadLink(link)) {
        } catch (final WikiPageNotFound e) {
            throw e;
        } catch (final Exception e) {
            log.log(Level.WARNING, "Failed to validate link {0}", link);
            log.log(Level.FINE, "Failed to validate link " + link, e);
        }
    }
}
