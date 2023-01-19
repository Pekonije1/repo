package com.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final AtomicInteger atomicInt = new AtomicInteger((new Random()).nextInt(1000000));

    public IdGenerator() {
    }

    public static Long getNewId() {
        long time = System.currentTimeMillis();
        int num = atomicInt.getAndIncrement() % 1000000;
        return time * 1000000L + (long) num;
    }

    public static Long getIdThatIsNotInSet(Set<Long> usedIds) {
        Long newId;
        for (newId = getNewId(); usedIds.contains(newId); newId = getNewId()) {
        }

        return newId;
    }

    public static UniqueIDGenerator getUniqueIdGenerator() {
        return new UniqueIDGenerator();
    }

    public static class UniqueIDGenerator {
        private Set<Long> issuedIds;

        private UniqueIDGenerator() {
            this.issuedIds = new HashSet();
        }

        public Long getNewId() {
            Long newId = IdGenerator.getIdThatIsNotInSet(this.issuedIds);
            this.issuedIds.add(newId);
            return newId;
        }
    }
}
