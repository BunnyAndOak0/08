package edu.kmust.bully.publish.schedule;

import edu.kmust.bully.publish.service.IModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Slf4j
public class CacheSchedule {
    @Autowired
    private IModelService modelService;

    public static final Object LOCK_CACHE = new Object();
    public static final Object LOCK_CACHE_MODEL = new Object();

    @Scheduled(initialDelay = 10 * 60 * 1000, fixedRate = 10 * 60 * 1000)
    public void clearCache() {
        synchronized (LOCK_CACHE) {
            modelService.getCachedListByNotIsAnonymousness().clear();
            log.debug("clear listByNotIsAnonymousness cache ...");
            modelService.getCachedListByIsAnonymousness().clear();
            log.debug("clear listByIsAnonymousness cache ...");
            modelService.getCachedSoulList().clear();
            log.debug("clear soulList cache ...");
        }
    }

    @Scheduled(initialDelay = 1 * 60 * 1000, fixedRate = 1 * 60 * 1000)
    public void clearQuestionCache() {
        synchronized (LOCK_CACHE_MODEL) {
            modelService.getCachedMostLikes().clear();
            log.debug("clear mostLikesModel cache ...");
        }
    }
}
