package com.connor.hpc;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/**
 * Dining Philosophers problem
 *
 */
public class App {
    public static void main(String[] args) {
        // pass reference to dining table 
        String name;
        String[] names = new String[]{"Harry", "Ben", "Sally", "Billy", "Laura"};
        DiningTable diningTable = new DiningTable();
        Philosopher[] philosophers = new Philosopher[5];
        try { 
            ExecutorService es = Executors.newCachedThreadPool();
            for(int i=0;i<5;i++)
                es.execute(new Philosopher(names[i],i,diningTable));
                es.shutdown();
            boolean finished = es.awaitTermination(3, TimeUnit.SECONDS);
            if (!finished) {
                System.out.println("The philosophers got stuck");
                es.shutdownNow();
            }
        } catch (Exception e) {
            System.out.println(String.format("%s",e.toString()));
            System.exit(1);
        }

    }
}
