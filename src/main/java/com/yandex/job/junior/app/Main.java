package com.yandex.job.junior.app;

import com.yandex.job.junior.app.logic.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void test(PhotoPushedEventHandler handler){
        List<PhotoPusher> list = new ArrayList<>();
        int pusherNumber = 5;
        for (int i = 0; i < pusherNumber; i++)
            list.add(new PhotoPusher());

        /**
         * This value shoul be ignored. I added this just for testing thread safety in the code below.
         */
        long ingore = 0;

        list.forEach(handler::observePhotoPusher);
        Random idGenerator = new Random();
        int counter = 20_000;
        for(int i = 0; i < 20_000; i++){
            list.forEach(photoPusher -> photoPusher.push(new Photo(idGenerator.nextInt())));
            /**
             * This is "toy" test for thread safety, just for me.
             * Ignore it
             */
            if (i % 50 == 0){
                ingore = handler.countPhotosPushedLastMinute();
            }
        }
    }

    /**
     * This is just for testing, and showing and example
     */
    // TODO: add Unit testing
    public static void main(String[] args) {
        PhotoPushedEventHandler handler =
                new PhotoPushedEventHandler(new EventCollector(), new EventByTimeCounter<Photo>());

        /**
         * And this is "toy" test for thread safety as well
         */

        Thread a = new Thread(() -> test(handler));
        Thread b = new Thread(() -> test(handler));
        Thread c = new Thread(() -> test(handler));
        Thread d = new Thread(() -> test(handler));

        a.start();
        b.start();
        c.start();
        d.start();
        try {
            a.join();
            b.join();
            c.join();
            d.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * If we have here 400_000, then everything is OK.
         *
         * Why 400_00?
         *
         * 4 threads, each has 5 PhotoPusher, each PhotoPusher pushes 20_000 times.
         * 5 * 20_000 * 4 = 400_000
         */
        System.out.println(handler.countPhotosPushedLastMinute());

        /**
         * This is example of usage this observer.
         * First create observable pusher.
         * Then we need an observer, which we tell to observe pusher
         * After that we do push photo, i.e. event is created.
         * And then we ask observer to count pusher in last minute
         */
        PhotoPusher pusher = new PhotoPusher();
        PhotoPushedEventHandler observer =
                new PhotoPushedEventHandler(new EventCollector(), new EventByTimeCounter<Photo>());
        observer.observePhotoPusher(pusher);
        pusher.push(new Photo(new Random().nextLong()));
        System.out.println(observer.countPhotosPushedLastMinute());
    }
}
