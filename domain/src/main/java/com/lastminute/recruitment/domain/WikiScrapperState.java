package com.lastminute.recruitment.domain;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class WikiScrapperState {

    private final Set<String> processedLinks;
    private final Queue<String> linksToProcess;

    public WikiScrapperState() {
        this.processedLinks = new HashSet<>();
        this.linksToProcess = new LinkedList<>();
    }

    public void addLinksToProcess(final List<String> links) {
        for (final var link : links) {
            addLinkToProcess(link);
        }
    }

    public void addLinkToProcess(final String link) {
        if (!isLinkProcessed(link)) {
            processedLinks.add(link);
            linksToProcess.add(link);
        }
    }

    private boolean isLinkProcessed(final String link) {
        return processedLinks.contains(link);
    }

    public String pollLink() {
        return linksToProcess.poll();
    }

    public boolean hasLinksToProcess() {
        return !linksToProcess.isEmpty();
    }
}
