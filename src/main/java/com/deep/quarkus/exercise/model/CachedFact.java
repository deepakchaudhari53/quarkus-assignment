package com.deep.quarkus.exercise.model;

import jakarta.json.bind.annotation.JsonbProperty;

import java.util.concurrent.atomic.AtomicInteger;

public class CachedFact {
    @JsonbProperty("text")
    private String text;
    private String shortenedUrl;
    @JsonbProperty("permalink")
    private String link;
    private final AtomicInteger accessCount = new AtomicInteger(0);

    public CachedFact() {}

    public CachedFact(String text, String shortenedUrl, String link) {
        this.text = text;
        this.shortenedUrl = shortenedUrl;
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getShortenedUrl() {
        return shortenedUrl;
    }

    public void setShortenedUrl(String shortenedUrl) {
        this.shortenedUrl = shortenedUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void incrementAccessCount() {
        accessCount.incrementAndGet();
    }

    public int getAccessCount() {
        return accessCount.get();
    }
}
