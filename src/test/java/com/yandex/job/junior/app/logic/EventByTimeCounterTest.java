package com.yandex.job.junior.app.logic;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EventByTimeCounterTest {

    static EventByTimeCounter<Photo> counter = new EventByTimeCounter<>();
    static Queue<Event<Photo>> events = new ConcurrentLinkedQueue<>();

    @BeforeClass
    public static void setUp(){
        LocalDateTime date = LocalDateTime.now();
        events.offer(new PhotoPushedEvent(date.minusMinutes(5), null));
        events.offer(new PhotoPushedEvent(date.minusMinutes(6), null));
        events.offer(new PhotoPushedEvent(date.minusMinutes(7), null));

        events.offer(new PhotoPushedEvent(date.minusSeconds(1), null));
        events.offer(new PhotoPushedEvent(date.minusSeconds(3), null));
        events.offer(new PhotoPushedEvent(date.minusSeconds(6), null));

        events.offer(new PhotoPushedEvent(date.minusHours(1), null));
        events.offer(new PhotoPushedEvent(date.minusHours(2), null));
        events.offer(new PhotoPushedEvent(date.minusHours(3), null));

        events.offer(new PhotoPushedEvent(date.minusDays(10), null));
        events.offer(new PhotoPushedEvent(date.minusDays(11), null));
    }

    @Test
    public void testGetEventsOccurredBefore(){
        counter.setEvents(events);
        LocalDateTime now = LocalDateTime.now();
        assertTrue (counter.getEventsOccurredAfter(now.minusMinutes(1)).count() == 3);
        assertTrue (counter.getEventsOccurredAfter(now.minusHours(1)).count() == 6);
        assertTrue (counter.getEventsOccurredAfter(now.minusDays(1)).count() == 9);
    }

    @Test
    public void testCountEventsOccurredLastMinute(){
        counter.setEvents(events);
        assertTrue(counter.countEventsOccurredLastMinute() == 3);
    }

    @Test
    public void testCountEventsOccurredLastHour(){
        counter.setEvents(events);
        assertTrue(counter.countEventsOccurredLastHour() == 6);
    }

    @Test
    public void testCountEventsOccurredLastDay(){
        counter.setEvents(events);
        assertTrue(counter.countEventsOccurredLastDay() == 9);
    }
}