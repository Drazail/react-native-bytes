package com.drazail.RNBytes.Utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToRunnableTest {
    private String Ran = "False";

    private void setRan() {
        Ran = "True";
    }

    @Test
    void run() throws InterruptedException {

        ToRunnable runnable = new ToRunnable(this::setRan);
        runnable.run();
        Thread.sleep(100);
        assertEquals(Ran, "True");
    }
}