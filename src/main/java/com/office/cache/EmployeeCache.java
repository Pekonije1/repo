package com.office.cache;

import com.office.entities.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class EmployeeCache {
    private static final String EMPLOYEE_KEY = "emp_";

    private final RedisTemplate<String, Employee> redisTemplate;

    public EmployeeCache(RedisTemplate<String, Employee> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Employee getEmployeeFromCache(Long employeeId) {
        String key = EMPLOYEE_KEY + "emp_";
        ValueOperations<String, Employee> workPositionCache = redisTemplate.opsForValue();
        return Boolean.TRUE.equals(redisTemplate.hasKey(key)) ? workPositionCache.get(key) : null;
    }

    /**
     * This method is used to store employee in cache.
     * Since this method will be invoked after employee is stored into the database we do not need to check if parameter is valid.
     *
     * @param employee the employee
     */
    @Async
    public void putInCache(Employee employee) {
        String key = EMPLOYEE_KEY + employee.getId();
        ValueOperations<String, Employee> workPositionCache = redisTemplate.opsForValue();
        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            log.info("Caching employee with id: " + employee.getId());
            workPositionCache.set(key, employee, 10, TimeUnit.MINUTES);
        }
    }

    @Async
    public void deleteFromCache(Employee employee) {
        String key = EMPLOYEE_KEY + employee.getId();
        Boolean hasKey = redisTemplate.hasKey(key);
        if (Boolean.TRUE.equals(hasKey)) {
            redisTemplate.delete(key);
            log.info("EmployeeServiceImpl.deletePost() : cache delete ID >> " + employee.getId());
        }
    }
}

