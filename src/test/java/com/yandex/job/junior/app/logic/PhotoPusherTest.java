package com.yandex.job.junior.app.logic;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class PhotoPusherTest {

    static EventByTimeCounter<Photo> counter = new EventByTimeCounter<>();
    static EventCollector<Photo> collector = new EventCollector<>();
    static PhotoPushedEventHandler handler = new PhotoPushedEventHandler(collector, counter);
    static int numberOfPhotos = 20;

    @BeforeClass
    public static void setUp(){
        PhotoPusher pusher = new PhotoPusher();
        handler.observePhotoPusher(pusher);
        Random idGenerator = new Random();
        for(int i = 0; i < numberOfPhotos; i++){
            pusher.push(new Photo(idGenerator.nextInt()));
        }
    }

    @Test
    public void testPushNotObserved(){
        PhotoPusher pusher = new PhotoPusher();
        pusher.push(null);
        assertTrue (handler.countPhotosPushedLastMinute() == numberOfPhotos);
    }

    @Test
    public void testPushObserved(){
        PhotoPusher pusher = new PhotoPusher();
        handler.observePhotoPusher(pusher);
        pusher.push(null);
        numberOfPhotos++;
        assertTrue (handler.countPhotosPushedLastMinute() == numberOfPhotos);

        pusher.push(null);
        numberOfPhotos++;
        assertTrue (handler.countPhotosPushedLastMinute() == numberOfPhotos);
    }
}
