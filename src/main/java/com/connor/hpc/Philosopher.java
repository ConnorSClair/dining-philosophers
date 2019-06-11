package com.connor.hpc;

import java.util.concurrent.locks.ReentrantLock;

public class Philosopher extends Thread {
    private Thread thread;
    private String name;
    private DiningTable table;
    private int seatNumber;
    private boolean hasEaten = false;

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
        try{
            pickUp(Direction.LEFT);
            pickUp(Direction.RIGHT);
            eat();
            putDown(Direction.LEFT);
            putDown(Direction.RIGHT);
            leaveTable();
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

    private void leaveTable() {
        System.out.println(String.format("%s left the table", name));
    }

    private void pickUp(Direction direction) {
        try {
            while (true) {
                if (accessFork(direction).getHoldCount() == 0) {
                accessFork(direction).lock();
                System.out.println(String.format("fork to %s of %s is picked up",direction.toString(),name));
                Thread.sleep(100);
                } 
                if (Thread.interrupted()) {
                    System.exit(1);
                }
            }
        } catch (Exception e) {
        }
    }

    private void putDown(Direction direction) throws InterruptedException {
        accessFork(direction).unlock();
        System.out.println(String.format("fork to %s of %s is put down",direction.toString(),name));
        Thread.sleep(100);
    }

    private void eat() throws InterruptedException {
        if (accessFork(Direction.LEFT).isHeldByCurrentThread() && accessFork(Direction.RIGHT).isHeldByCurrentThread()) {
            hasEaten = true;
            System.out.println(String.format("%s ate dinner",name));
            Thread.sleep(1000);
        }
    }

    public void start() {
        if (thread == null) { 
            thread = new Thread(this, name);
            thread.start();
        }
    }

}