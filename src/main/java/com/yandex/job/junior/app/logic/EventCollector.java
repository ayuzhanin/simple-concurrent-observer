package com.yandex.job.junior.app.logic;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This class is used to store events, which was observed in observables
 *
 * @param <T>
 */
public class EventCollector<T> implements Observer {

    /**
     * List of observables
     */
    private List<Observable> observed;
    /**
     * List of events
     */
    private Queue<Event<T>> events;

    /**
     * Note: ConcurrentLinkedQueue is used here.
     */
    public EventCollector() {
        observed = new LinkedList<>();
        events = new ConcurrentLinkedQueue<>();
    }

    public void observe(Observable observable) {
        observable.addObserver(this);
        observed.add(observable);
    }

    @Override
    public void update(Observable o, Object event) {
        events.offer((Event<T>) event);
    }

    public Queue<Event<T>> getEvents() {
        return events;
    }

    public void clearEvents() {
        events.clear();
    }
}
