package com.yandex.job.junior.app.logic;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class EventCollectorTest {
    static EventCollector<Photo> collector = new EventCollector<>();

    @Test
    public void update() {
        PhotoPusher a = new PhotoPusher();
        PhotoPusher b = new PhotoPusher();
        collector.observe(a);
        collector.observe(b);
        a.push(new Photo(1));
        a.push(new Photo(2));
        a.push(new Photo(3));
        b.push(new Photo(1));

        assertTrue(collector.getEvents().size() == 4);

        List<Event<Photo>> events = collector.getEvents().stream().collect(Collectors.toList());
        assertTrue (events.stream().filter(event -> event.getValue().getId() == 1).count() == 2);
        assertTrue (events.stream().filter(event -> event.getValue().getId() == 2).count() == 1);
        assertTrue (events.stream().filter(event -> event.getValue().getId() == 3).count() == 1);
    }
}
