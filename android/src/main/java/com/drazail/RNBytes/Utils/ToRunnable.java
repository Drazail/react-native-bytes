package com.drazail.RNBytes.Utils;

public class ToRunnable implements Runnable {

    private Runnable task;

    public ToRunnable(Runnable task) {
        this.task = task;

    }

    public void run() {
        new Thread(task).start();
    }
}