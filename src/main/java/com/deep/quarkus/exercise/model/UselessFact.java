package com.deep.quarkus.exercise.model;

import jakarta.json.bind.annotation.JsonbProperty;

public class UselessFact {
    private String id;
    private String text;
    private String source;
    private String source_url;
    @JsonbProperty("permalink")
    private String link;
    private Integer accessCount;

    public void UserlessFact() {}

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getSource_url() {
        return source_url;
    }
    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }

    public Integer getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(Integer accessCount) {
        this.accessCount = accessCount;
    }
}
