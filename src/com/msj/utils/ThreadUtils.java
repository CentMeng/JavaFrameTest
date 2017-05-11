package com.msj.utils;

/**
 * @author Vincent.M mengshaojie@188.com
 * @date 2017/5/11 下午8:20
 * @copyright ©2017 孟少杰 All Rights Reserved
 * @desc 线程Utils
 */
public class ThreadUtils {
    public static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
        }
    }

    public static void waitForMillis(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
        }
    }
}
