package com.office.cache;

import com.office.entities.WorkPosition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class WorkPositionCache {
    private static final String WORK_POSITION_KEY = "wp_";

    private final RedisTemplate<String, WorkPosition> redisTemplate;

    public WorkPositionCache(RedisTemplate<String, WorkPosition> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public WorkPosition getWorkPositionFromCache(Long workPositionId) {
        String key = WORK_POSITION_KEY + workPositionId;
        ValueOperations<String, WorkPosition> workPositionCache = redisTemplate.opsForValue();
        return Boolean.TRUE.equals(redisTemplate.hasKey(key)) ? workPositionCache.get(key) : null;
    }

    @Async
    public void putWorkPositionInCache(WorkPosition workPosition) {
        String key = WORK_POSITION_KEY + workPosition.getId();
        ValueOperations<String, WorkPosition> workPositionCache = redisTemplate.opsForValue();
        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            log.info("Caching work position with id: " + workPosition.getId());
            workPositionCache.set(key, workPosition, 10, TimeUnit.MINUTES);
        }
    }
}
