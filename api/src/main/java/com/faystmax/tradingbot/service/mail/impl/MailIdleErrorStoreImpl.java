package com.faystmax.tradingbot.service.mail.impl;

import com.faystmax.tradingbot.db.entity.User;
import com.faystmax.tradingbot.service.mail.MailIdleErrorStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class MailIdleErrorStoreImpl implements MailIdleErrorStore {
    private final Map<User, Throwable> errorByUserMap = new ConcurrentHashMap<>();

    @Override
    public void put(User user, Throwable throwable) {
        errorByUserMap.put(user, throwable);
    }

    @Override
    public String getErrorMessage(User user) {
        Throwable throwable = errorByUserMap.get(user);
        return throwable == null ? "No Errors!" : throwable.getClass() + ": " + throwable.getMessage();
    }

    @Override
    public void clear(User user) {
        errorByUserMap.remove(user);
    }
}
