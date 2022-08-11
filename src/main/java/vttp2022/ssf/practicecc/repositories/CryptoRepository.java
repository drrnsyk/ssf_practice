package vttp2022.ssf.practicecc.repositories;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class CryptoRepository {
    
    @Value("${crypto.cache.duration}")
    private Long cacheTime;

    @Autowired
    @Qualifier("redislab")
    private RedisTemplate<String, String> redisTemplate;

    public void save(String symbol, String payload) {
        ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
        valueOp.set(symbol.toUpperCase(), payload, Duration.ofMinutes(cacheTime));
    }

        public Optional<String> get(String symbol) {
        // give the city and retrive the payload from redis
        ValueOperations<String, String> valueOp = redisTemplate.opsForValue();
        String value = valueOp.get(symbol.toUpperCase());
        if (null == value)
            return Optional.empty(); // empty box
        return Optional.of(value); // box with data

    }

}
