package net.univr.pushi.jxatmosphere.utils;

import java.util.Random;

/**
 * author : Administrator wl
 * e-mail : 389456264@qq.com
 * time   : 2018/08/01
 * desc   :
 * version: 1.0
 */


public class RandomNum {
    public static int getRandomNum() {
        int max = 255;
        Random random = new Random();
        int num = random.nextInt(max) + 1;
        return num;
    }
}
