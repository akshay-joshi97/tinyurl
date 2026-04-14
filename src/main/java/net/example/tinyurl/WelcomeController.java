package net.example.tinyurl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to Spring World!";
    }
    @GetMapping("/redis-test")
    public String testRedis() {
        redisTemplate.opsForValue().set("test", "hello");
        return redisTemplate.opsForValue().get("test");
    }
}
