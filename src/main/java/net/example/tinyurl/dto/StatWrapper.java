package net.example.tinyurl.dto;

import java.time.LocalDateTime;

public class StatWrapper {
    public String originalUrl;
    public String shortCode;
    public Integer clickCount;
    public LocalDateTime createdAt;
    public LocalDateTime lastAccessedAt;
}
