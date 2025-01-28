package com.url.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.url.dtos.UrlResponseDTO;
import com.url.model.UrlModel;
import com.url.service.UrlService;

@RestController
@CrossOrigin("*")
@RequestMapping("/url")
public class UrlController {
	
	@Autowired
	UrlService urlService;
	
	
//    @PostMapping("/shorten")
//    public ResponseEntity<UrlResponseDTO> shortenUrl(@RequestParam("actualUrl") String actualURL) {
//    	
//        UrlModel shortened = urlService.createShortUrl(actualURL);
//        
//        UrlResponseDTO response = new UrlResponseDTO(
//            shortened.getActualURL(), 
//            shortened.getShortURL()
//        );
//        
//        return ResponseEntity.ok(response);
//    }
	
	@PostMapping("/shorten")
    public ResponseEntity<UrlResponseDTO> shortenUrl(@RequestParam(name = "actualUrl") String actualUrl) {
		
        UrlModel shortened = urlService.createShortUrl(actualUrl);
        
        UrlResponseDTO response = new UrlResponseDTO(
            shortened.getActualURL(),
            shortened.getShortURL() // Combine with short.ly domain
        );
        
        return ResponseEntity.ok(response);
    }


//    @GetMapping("/{shortUrl}")
//    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortUrl) {
//    	
//        String originalUrl = urlService.getOriginalUrl(shortUrl);
//        return ResponseEntity.status(302).header("Location", originalUrl).build();
//    }
//	
    
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortCode) {
        String originalUrl = urlService.getOriginalUrl(shortCode);
        
        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)  // or HttpStatus.FOUND for temporary redirect
                            .header(HttpHeaders.LOCATION, originalUrl)
                            .build();
    }
    
    @GetMapping("/redirect/{shortCode}")
    public ResponseEntity<Void> redirectToActualUrl(@PathVariable("shortCode") String shortCode) {
    	
        UrlModel urlModel = urlService.getUrlByShortCode(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(urlModel.getActualURL()))
            .build();
    }
	

}
