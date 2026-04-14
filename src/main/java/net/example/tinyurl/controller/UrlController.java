package net.example.tinyurl.controller;

import net.example.tinyurl.dto.StatWrapper;
import net.example.tinyurl.dto.UrlRequest;
import net.example.tinyurl.model.UrlMapping;
import net.example.tinyurl.service.UrlService;
import net.example.tinyurl.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UrlController {
    public final UrlService urlService;
    @Autowired
    public UrlRepository urlRepository;

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

    @GetMapping("/top")
    public List<UrlMapping> getTopUrls(){
        List<UrlMapping> top5ByOrderByClickCountDesc = urlRepository.findTop5ByOrderByClickCountDesc();
        return top5ByOrderByClickCountDesc;
    }

    @GetMapping("/stats/{shortCode}")
    public StatWrapper getStats(@PathVariable String shortCode){
        UrlMapping url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        StatWrapper wrapper = new StatWrapper();
        wrapper.shortCode = url.getShortCode();
        wrapper.originalUrl = url.getOriginalUrl();
        wrapper.createdAt = url.getCreatedAt();
        wrapper.lastAccessedAt = url.getLastAccessedAt();
        wrapper.clickCount = url.getClickCount();
        return wrapper;
    }
}
