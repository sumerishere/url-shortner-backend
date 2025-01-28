package com.url.dtos;

public class UrlResponseDTO {
    private String actualUrl;
    private String shortUrl;

    public UrlResponseDTO(String actualUrl, String shortUrl) {
        this.actualUrl = actualUrl;
        this.shortUrl = shortUrl;
    }

    // Getters and setters
    public String getActualUrl() {
        return actualUrl;
    }

    public void setActualUrl(String actualUrl) {
        this.actualUrl = actualUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}