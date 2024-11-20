package com.example.exceptiontt.thread;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest {
    public static void main(String[] args) {
        //线程创建三种方式
        //1:
        int i = 0;
        Thread thread = new Thread() {
            @Override
            public void run() {
                lockThread(0);
            }
        };
        new Thread(() -> lockThread(0)).start();
        thread.start();
    }

    public static synchronized void synchronizedThread(int i) {
        while (i < 5) {
            System.out.println(i);
            i++;
        }
    }
    public static void lockThread(int i){
        Lock lock = new ReentrantLock();
        lock.lock();
//        lock.tryLock(2, TimeUnit.SECONDS);
        while (i < 5) {
            System.out.println(i);
            i++;
        }
        lock.unlock();
    }
}
