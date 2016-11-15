package com.yandex.job.junior.app.logic;

import java.util.Observable;
import java.time.LocalDateTime;

/**
 * This is subclass ob Observable.
 * Instances ot that class triggers trigger observer when they changes
 */
public class PhotoPusher extends Observable {

    public void push(Photo photo /*, User user*/) {
        PhotoPushedEvent event = new PhotoPushedEvent(LocalDateTime.now(), photo);
        setChanged();
        notifyObservers(event);
    }

}
