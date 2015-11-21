/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 CodeRevisited.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.coderevisited.concurrency;

/**
 * User    : Suresh
 * Date    : 21/11/15
 * Version : v1
 */

/**
 * Demonstrates printing of HelloWorld with two threads
 */
public class HelloWorldTwoThreads {

    private final Object monitor = new Object();
    private volatile boolean isFirstThread = true;


    public void printHelloWorld(int loop) {
        Thread t1 = new Thread(new HelloRunnable(loop));
        Thread t2 = new Thread(new WorldRunnable(loop));

        t1.start();
        t2.start();


        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    class HelloRunnable implements Runnable {


        private final int loop;

        public HelloRunnable(int loop) {
            this.loop = loop;
        }

        @Override
        public void run() {
            synchronized (monitor) {
                for (int i = 0; i < loop; i++) {
                    try {
                        while (!isFirstThread) {
                            monitor.wait();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }


                    if (isFirstThread)
                        System.out.print("Hello ");
                    isFirstThread = false;
                    monitor.notify();

                }
            }

        }
    }


    class WorldRunnable implements Runnable {

        private final int loop;

        public WorldRunnable(int loop) {
            this.loop = loop;
        }

        @Override
        public void run() {
            synchronized (monitor) {
                for (int i = 0; i < loop; i++) {
                    try {
                        while (isFirstThread) {
                            monitor.wait();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }


                    if (!isFirstThread)
                        System.out.println("World!");
                    isFirstThread = true;
                    monitor.notify();
                }
            }

        }
    }


}
