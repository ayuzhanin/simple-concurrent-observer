package com.yandex.job.junior.app.logic;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;
import static org.junit.Assert.*;

public class PhotoPushedEventHandlerTest {

    static EventByTimeCounter<Photo> counter = new EventByTimeCounter<>();
    static EventCollector<Photo> collector = new EventCollector<>();
    static PhotoPushedEventHandler handler = new PhotoPushedEventHandler(collector, counter);

    @BeforeClass
    public static void setUp(){
        PhotoPusher pusher = new PhotoPusher();
        handler.observePhotoPusher(pusher);
        Random idGenerator = new Random();
        for(int i = 0; i < 20; i++){
            pusher.push(new Photo(idGenerator.nextInt()));
        }
    }

    @Test
    public void testCountPhotosPushedLastMinute(){
        assertTrue (handler.countPhotosPushedLastMinute() == 20);
    }
}