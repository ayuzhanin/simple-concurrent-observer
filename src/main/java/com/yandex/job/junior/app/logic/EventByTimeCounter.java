package com.yandex.job.junior.app.logic;

import java.time.LocalDateTime;
import java.util.Queue;
import java.util.stream.Stream;

/**
 * This is event by time counter class.
 * It is used for counting event over time. Counts events for last minute, hour and day.
 * @param <T>
 */
public class EventByTimeCounter<T> {

    /**
     * We use ConcurrentLinkedQueue here for thread safety
     */
    private Queue<Event<T>> events;

    public void setEvents(Queue<Event<T>> events) {
        this.events = events;
    }

    enum DurationType {MINUTE, HOUR, DAY}

    /**
     * This method created a LocalDateTime of a given one.
     * Important: newly created LocalDateTime variable's value point to earlier moment at time than given one.
     * @param moment a given moment of time.
     * @param duration number of units (see DurationType)
     * @param durationType type of units to be subtracted
     * @return LocalDateTime variable which points at earlier moment
     */
    public LocalDateTime earlierLocalDateTime(LocalDateTime moment, long duration, DurationType durationType) {
        switch (durationType) {
            case MINUTE:
                return moment.minusMinutes(duration);
            case HOUR:
                return moment.minusHours(duration);
            case DAY:
                return moment.minusDays(duration);
            default:
                return moment;
        }
    }

    /**
     * This method filters events which happened after given LocalDateTime
     * @param moment given moment of time
     * @return Stream of com.yandex.job.junior.app.logic.Event<T> which satisfy predicate (happened after moment in time)
     */
    public Stream<Event<T>> getEventsOccurredAfter(LocalDateTime moment){
        return events.stream().filter(event -> event.getLocalDateTime().isAfter(moment));
    }

    /**
     * This method is quite straightforward
     * @return
     */
    public long countEventsOccurredLastMinute() {
        LocalDateTime minuteEarlier = earlierLocalDateTime(LocalDateTime.now(), 1, DurationType.MINUTE);
        return getEventsOccurredAfter(minuteEarlier).count();
    }

    public long countEventsOccurredLastHour() {
        LocalDateTime hourEarlier = earlierLocalDateTime(LocalDateTime.now(), 1, DurationType.HOUR);
        return getEventsOccurredAfter(hourEarlier).count();
    }

    public long countEventsOccurredLastDay() {
        LocalDateTime dayEarlier = earlierLocalDateTime(LocalDateTime.now(), 1, DurationType.DAY);
        return getEventsOccurredAfter(dayEarlier).count();
    }
}