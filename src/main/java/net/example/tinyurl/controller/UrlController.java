package net.example.tinyurl.controller;

import net.example.tinyurl.dto.UrlRequest;
import net.example.tinyurl.service.UrlService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlController {
    public final UrlService urlService;

    public UrlController(UrlService urlService){
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public String shortenUrl(@RequestBody UrlRequest urlRequest){
        return urlService.shortenUrl(urlRequest.getUrl());
    }

    @GetMapping("/{shortCode}")
    public org.springframework.http.ResponseEntity<Void> getOriginalUrl(@PathVariable String shortCode){

        String originalUrl = urlService.getOriginalUrl(shortCode);
        return org.springframework.http.ResponseEntity.
                status(302).header("Location", originalUrl).build();
    }
}
