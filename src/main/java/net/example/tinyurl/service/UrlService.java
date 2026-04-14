package net.example.tinyurl.service;

import net.example.tinyurl.util.Base62Encoder;
import net.example.tinyurl.model.UrlMapping;
import net.example.tinyurl.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlService {
    private final UrlRepository urlRepository;
    @Autowired
    private StringRedisTemplate redisTemplate;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenUrl(String originalUrl){
        Optional<UrlMapping> existing = urlRepository.findByOriginalUrl(originalUrl);
        if(existing.isPresent()) {
            return existing.get().getShortCode();
        }else{
            UrlMapping url = new UrlMapping();
            url.setOriginalUrl(originalUrl);
            url = urlRepository.save(url);
            Long id = url.getId();
            String shortCode = Base62Encoder.encode(id);
            url.setShortCode(shortCode);
            urlRepository.save(url);
            return shortCode;
        }
    }

    public String getOriginalUrl(String shortCode){
        String originalUrl;
        String cache = redisTemplate.opsForValue().get(shortCode);
        if(cache != null){
            originalUrl = cache;
        }else{
            UrlMapping url = urlRepository.findByShortCode(shortCode)
                    .orElseThrow(() -> new RuntimeException("Short URL not found"));
            originalUrl = url.getOriginalUrl();
            redisTemplate.opsForValue().set(shortCode, url.getOriginalUrl());
        }
        UrlMapping url = urlRepository.findByShortCode(shortCode).orElseThrow();
        url.setClickCount(url.getClickCount() + 1);
        url.setLastAccessedAt(LocalDateTime.now());
        urlRepository.save(url);
        return originalUrl;
    }
}
