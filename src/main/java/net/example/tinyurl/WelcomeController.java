package net.example.tinyurl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.redis.core.RedisTemplate;


@RestController
public class WelcomeController {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to Spring World!";
    }
    @GetMapping("/redis-test")
    public String testRedis() {
        redisTemplate.opsForValue().set("test", "hello");
        return (String)redisTemplate.opsForValue().get("test");
    }
}
