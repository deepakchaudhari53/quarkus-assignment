package com.deep.quarkus.exercise.service;

import com.deep.quarkus.exercise.model.CachedFact;
import com.deep.quarkus.exercise.model.UselessFact;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class FactService {
    private final ConcurrentHashMap<String, CachedFact> cache = new ConcurrentHashMap<>();

    public String generateShortenedUrl(UselessFact uselessFact) {

        String shortenedUrl =  Integer.toHexString(uselessFact.hashCode());
        CachedFact fact = new CachedFact(uselessFact.getText(), shortenedUrl, uselessFact.getLink());
        cache.put(shortenedUrl, fact);

        return shortenedUrl;
    }

    public CachedFact getFact(String shortenedUrl) {
        CachedFact fact = cache.get(shortenedUrl);
        if (fact != null) {
            fact.incrementAccessCount();
        }
        return fact;
    }

    public CachedFact redirectToOriginal(String shortenedUrl) {
        CachedFact fact = cache.get(shortenedUrl);
        if (fact != null) {
            fact.incrementAccessCount();
        }
        return fact;
    }

    public HashMap getAllFacts() {
        return new HashMap<>(cache);
    }
}
