package com.connor.hpc;

import java.util.concurrent.locks.ReentrantLock;

public class Philosopher extends Thread {
    private Thread t;
    private String name;
    private DiningTable table;
    private int seatNumber;
    private boolean hasEaten = false;
    private boolean leftFork = false;
    private boolean rightFork = false;

    private enum Direction {
        LEFT,
        RIGHT
    }

    public Philosopher(String name,int seat, DiningTable table) {
        this.name = name;
        this.table = table;
        this.seatNumber = seat;
        System.out.println(String.format("New Philosopher - %s - Created",name));
    }

    public void run() {
        try {
            pickupLeft();
            sleep(1000);
            pickupRight();
            eat();
            putDownLeft();
            putDownRight();
            
        } catch(Exception e) {
            System.out.println(String.format("error - %s",e.toString()));
        }
    }

    private ReentrantLock accessFork(Direction direction) {
        if (direction == direction.LEFT) {
            return table.forks[seatNumber];
        } else {
            return table.forks[(seatNumber + 1) % 5];
        }
    }

    // diningTable.forks[i]
    private void pickupLeft() {
        accessFork(Direction.LEFT).lock();
        System.out.println(String.format("fork to left of %s is picked up",name));
        // if left not taken, pickup otherwise nothing
    }
    // diningTable.forks[i+1] 
    private void pickupRight() {
        accessFork(Direction.RIGHT).lock();
        System.out.println(String.format("fork to right of %s is picked up",name));
    }

    //
    private void putDownLeft() {
        accessFork(Direction.LEFT).unlock();
        System.out.println(String.format("fork to left of %s is put down",name));
    }

    //
    private void putDownRight() {
        accessFork(Direction.RIGHT).unlock();
        System.out.println(String.format("fork to right of %s is put down",name));
    }

    private void eat() {
        if (table.forks[(seatNumber + 1) % 5].isHeldByCurrentThread() && table.forks[seatNumber].isHeldByCurrentThread()) {
            hasEaten = true;
            System.out.println(String.format("%s ate dinner",name));
        }
    }


    public void start() {
        if (t == null) { 
            t = new Thread(this, name);
            t.start();
        }
    }

}