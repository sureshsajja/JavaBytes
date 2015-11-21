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
 * Runtime exception in one of the threads doesn't stop the execution of main thread or other threads
 */
public class ThreadExceptions {

    public static void main(String[] args) {

        Thread t1 = new Thread(new GoodRunnable());
        Thread t2 = new Thread(new GoodRunnable());
        Thread t3 = new Thread(new GoodRunnable());
        Thread t4 = new Thread(new GoodRunnable());
        Thread t5 = new Thread(new BadRunnable());

        t1.start();
        t2.start();
        t5.start();
        try {
            t5.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        t3.start();
        t4.start();
    }


    static class GoodRunnable implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println("Hi");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    static class BadRunnable implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println("Bad");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("Very bad");
        }
    }
}
