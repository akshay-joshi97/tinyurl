package net.example.tinyurl.service;

import net.example.tinyurl.util.Base62Encoder;
import net.example.tinyurl.model.UrlMapping;
import net.example.tinyurl.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

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
        Optional<UrlMapping> existing = urlRepository.findByShortCode(shortCode);
        if(existing.isPresent()) {
            UrlMapping url = existing.get();
            url.setClickCount(url.getClickCount() + 1);
            urlRepository.save(url);
            return url.getOriginalUrl();
        }else{
            throw new RuntimeException("Short URL not found");
        }
    }
}
