package net.example.tinyurl.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String shortCode;
    private String originalUrl;
    private int clickCount;
    private LocalDateTime createdAt;

    public UrlMapping(){
        this.createdAt = LocalDateTime.now();
        this.clickCount = 0;
    }

    public String getShortCode() {
        return this.shortCode;
    }

    public String getOriginalUrl(){
        return this.originalUrl;
    }

    public void setOriginalUrl(String originalUrl){
        this.originalUrl = originalUrl;
    }

    public void setShortCode(String shortCode){
        this.shortCode = shortCode;
    }

    public void setClickCount(int clickCount){
        this.clickCount = clickCount;
    }

    public int getClickCount(){
        return this.clickCount;
    }

    public Long getId(){
        return this.id;
    }
}
