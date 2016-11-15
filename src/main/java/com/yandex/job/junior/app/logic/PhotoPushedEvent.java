package com.yandex.job.junior.app.logic;

import java.time.LocalDateTime;

/**
 * This is an com.yandex.job.junior.app.logic.Event implementation
 * Just for examples and testing
 */

public class PhotoPushedEvent implements Event<Photo>{
    private LocalDateTime localDateTime;
    private Photo photo;

    public PhotoPushedEvent(LocalDateTime localDateTime, Photo photo) {
        this.localDateTime = localDateTime;
        this.photo = photo;
    }

    @Override
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    @Override
    public Photo getValue() {
        return photo;
    }
}
