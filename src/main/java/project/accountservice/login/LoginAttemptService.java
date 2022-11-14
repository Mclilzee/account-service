package project.accountservice.login;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class LoginAttemptService {

    private final int MAX_ATTEMPTS = 5;
    private final LoadingCache<String, Integer> attemptCache;

    public LoginAttemptService() {
        attemptCache = CacheBuilder.newBuilder()
                .build(getCacheLoader());
    }

    private CacheLoader<String, Integer> getCacheLoader() {
        return new CacheLoader<>() {
            @Override
            public Integer load(String key) throws Exception {
                return 0;
            }
        };
    }

    public void loginSucceeded(String key) {
        attemptCache.invalidate(key);
    }

    public void loginFailed(String key) {
        int attempts = 0;
        try {
            attempts = attemptCache.get(key);
        } catch (ExecutionException ignored) {
        }
        attempts++;
        attemptCache.put(key, attempts);
    }

    public boolean isBlocked(String key) {
        try {
            return attemptCache.get(key) >= MAX_ATTEMPTS;
        } catch (ExecutionException ex) {
            return false;
        }
    }
}
