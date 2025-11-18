package com.lastminute.recruitment.domain;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class WikiScrapperState {
    private static final int EXPECTED_NUMBER_OF_ELEMENTS = 5;

    private final BloomFilter<String> processedLinks;
    private final Queue<String> linksToProcess;

    public WikiScrapperState() {
        this.processedLinks = BloomFilter.create(
                Funnels.stringFunnel(StandardCharsets.UTF_8),
                EXPECTED_NUMBER_OF_ELEMENTS
        );

        this.linksToProcess = new LinkedList<>();
    }

    public void addLinksToProcess(final List<String> links) {
        for (final var link : links) {
            addLinkToProcess(link);
        }
    }

    public void addLinkToProcess(final String link) {
        if (!isLinkProcessed(link)) {
            processedLinks.put(link);
            linksToProcess.add(link);
        }
    }

    private boolean isLinkProcessed(final String link) {
        return processedLinks.mightContain(link);
    }

    public String pollLink() {
        return linksToProcess.poll();
    }

    public boolean hasLinksToProcess() {
        return !linksToProcess.isEmpty();
    }
}
