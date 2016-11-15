package com.yandex.job.junior.app.logic;

/**
 * This class handles collecting events of pushing photos and
 * counting these events in custom manner (by time)
 *
 * To follow SOLID's Single responsibility principle
 * the best implementation (in my opinion) should contain separated
 * EvenCollector and com.yandex.job.junior.app.logic.EventByTimeCounter, which I present here.
 *
 * The code bellow is quite straightforward
 */
public class PhotoPushedEventHandler {
    private EventCollector collector;
    private EventByTimeCounter counter;

    public PhotoPushedEventHandler(EventCollector collector, EventByTimeCounter counter) {
        this.collector = collector;
        this.counter = counter;
    }

    public void observePhotoPusher(PhotoPusher photoPusher){
        collector.observe(photoPusher);
    }

    public long countPhotosPushedLastMinute(){
        counter.setEvents(collector.getEvents());
        return counter.countEventsOccurredLastMinute();
    }

    public long countPhotosPushedLastHour(){
        counter.setEvents(collector.getEvents());
        return counter.countEventsOccurredLastHour();
    }

    public long countPhotosPushedLastDay(){
        counter.setEvents(collector.getEvents());
        return counter.countEventsOccurredLastDay();
    }

    public void dropCollectedEvents(){
        collector.clearEvents();
    }
}