package com.example.demot1.log.log4j2;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: yichu.zhang
 * @Date: 2019-10-12 16:36
 */
@Slf4j
public class LogHandler implements Runnable {
    private final int index;
    private final int msgNum;
    private final CountDownLatch latch;

    public LogHandler(final int index, final int msgNum, final CountDownLatch latch){
        this.index = index;
        this.msgNum = msgNum;
        this.latch = latch;
    }

    @Override
    public void run() {
        for(int i = 0; i < this.msgNum; i++){
            log.info("abcdefghijklmnopqrstuvwxyz-abcdefghijklmnopqrstuvwxyz-abcdefghijklmnopqrstuvwxyz-abcdefghijklmnopqrstuvwxyz-abcdefghijklmnopqrstuvwxyz index = {}, i = {}", this.index, i);
        }
        latch.countDown();
    }
}
