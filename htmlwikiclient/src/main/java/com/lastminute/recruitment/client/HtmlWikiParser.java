package com.lastminute.recruitment.client;

import com.lastminute.recruitment.domain.WikiPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class HtmlWikiParser implements WikiParser {

    @Override
    public WikiPage parse(InputStream source) throws IOException {
        final var document = Jsoup.parse(source, null, "");
        return new WikiPage(
                extractTitle(document),
                extractContent(document),
                extractSelfLink(document),
                extractLinks(document)
        );
    }

    private String extractTitle(final Document document) {
        return document.title();
    }

    private String extractContent(final Document document) {
        return document.select("p.content").text();
    }

    private String extractSelfLink(final Document document) {
        return document.select("meta").attr("selflink");
    }

    private List<String> extractLinks(final Document document) {
        return document.select("ul.links a[href]").eachAttr("href");
    }
}
