package com.connor.hpc;

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
        for (int i = 0; i < names.length; i++) {
            name = names[i];
            philosophers[i] = new Philosopher(name,i,diningTable);
        }
        for (int i = 0; i < names.length; i++) {
            philosophers[i].start();
        }
        try { 
            // wait for philosophers to get stuck
            Thread.sleep(5000);
            diningTable.dinnerTime = false;

        } catch (Exception e) {
            System.out.println("%s",e.toString()));
        }

    }
}
