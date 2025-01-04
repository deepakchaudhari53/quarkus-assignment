package com.deep.quarkus.exercise.service;

import com.deep.quarkus.exercise.model.CachedFact;
import com.deep.quarkus.exercise.model.UselessFact;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

@QuarkusTest
public class FactServiceTest {

    @Inject
    FactService factService;

    @Test
    public void testGenerateShortenedUrl() {
        UselessFact uselessFact = new UselessFact();
        uselessFact.setText("text");
        uselessFact.setLink("link");
        String shortenedUrl = factService.generateShortenedUrl(uselessFact);
        Assertions.assertNotNull(shortenedUrl);
        Assertions.assertTrue( shortenedUrl.length() > 0); // Assuming hex string length
    }

    @Test
    public void testGetFact() {
        UselessFact uselessFact = new UselessFact();
        uselessFact.setText("text");
        uselessFact.setLink("link");
        String shortenedUrl = factService.generateShortenedUrl(uselessFact);
        CachedFact fact = factService.getFact(shortenedUrl);
        Assertions.assertNotNull(fact);
        Assertions.assertEquals(uselessFact.getText(), fact.getText());
    }

    @Test
    public void testRedirectToOriginal() {
        UselessFact uselessFact = new UselessFact();
        uselessFact.setText("text");
        uselessFact.setLink("link");
        String shortenedUrl = factService.generateShortenedUrl(uselessFact);
        CachedFact fact = factService.redirectToOriginal(shortenedUrl);
        Assertions.assertNotNull(fact);
        Assertions.assertEquals(uselessFact.getText(), fact.getText());
    }

    @Test
    public void testGetAllFacts() {
        UselessFact fact1 = new UselessFact();
        fact1.setText("text1");
        fact1.setLink("link1");
        UselessFact fact2 = new UselessFact();
        fact2.setText("text2");
        fact2.setLink("link2");
        factService.generateShortenedUrl(fact1);
        factService.generateShortenedUrl(fact2);

        HashMap<String, CachedFact> allFacts = factService.getAllFacts();
        Assertions.assertEquals(2, allFacts.size());
    }
}
