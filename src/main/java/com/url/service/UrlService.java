package com.url.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.url.model.UrlModel;
import com.url.repository.UrlRepository;

@Service
public class UrlService {
	
    @Autowired
    UrlRepository urlRepository;
	
    private static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 6;
    private static final String BASE_URL = "http://short.ly/";
    
    
    public UrlModel createShortUrl(String actualUrl) {
    	
        if (!isValidUrl(actualUrl)) {
            throw new IllegalArgumentException("Invalid URL format");
        }

        UrlModel existingUrl = urlRepository.findByActualURL(actualUrl);
        
        if (existingUrl != null) {
            return existingUrl;
        }

        String shortCode = generateShortUrl();
        while (urlRepository.findByShortURL(shortCode) != null) {
            shortCode = generateShortUrl();
        }

        UrlModel urlModel = new UrlModel();
        urlModel.setActualURL(actualUrl);
        urlModel.setShortURL(shortCode); // Store only the short code
        urlModel.setCreatedAt(LocalDateTime.now().toString());
        urlModel.setExpiryDate(LocalDateTime.now().plusDays(30).toString());

        return urlRepository.save(urlModel);
    }
    
    
    private String generateShortUrl() {
    	
        Random random = new Random();
        StringBuilder sb = new StringBuilder(SHORT_URL_LENGTH);
        
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            sb.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }
        
        return sb.toString();
    }
    
    
    @SuppressWarnings("deprecation")
	private boolean isValidUrl(String url) {
        try {
            new java.net.URL(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    
    public String getOriginalUrl(String shortCode) {
        // Remove base URL if present, we just need the code
        String code = shortCode.replace("http://short.ly/", "");
        
        UrlModel urlModel = urlRepository.findByShortURL("http://short.ly/" + code);
        if (urlModel == null) {
            throw new RuntimeException("URL not found");
        }
        
        return urlModel.getActualURL();
    }
    
    
    public UrlModel getUrlByShortCode(String shortCode) {
    	
        UrlModel urlModel = urlRepository.findByShortURL(shortCode);
        
        if (urlModel == null) {
            throw new RuntimeException("URL not found");
        }
        return urlModel;
    }

}
