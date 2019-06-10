package com.connor.hpc;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Dining Philosophers problem
 * Forks are either picked up (true) or on table (false) 
 * each index in forks points to the fork to the left of the seat index
 * e.g. index 1 indicates whether the fork to the left of the 2nd seat has been picked up
 */
public class DiningTable {
    ReentrantLock[] forks = new ReentrantLock[5];
    boolean dinnerTime = True
    public DiningTable() {
        for (int i = 0; i < forks.length; i++) {
            forks[i] = new ReentrantLock();
        }
    }
}