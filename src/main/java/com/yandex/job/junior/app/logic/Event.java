package com.yandex.job.junior.app.logic;

import java.time.LocalDateTime;

/**
 * This is event class. Contains LocalDateTime and object, which occurred that event
 * @param <T>
 */
public interface Event<T> {
    LocalDateTime getLocalDateTime();
    T getValue();
}
