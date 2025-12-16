package com.gpyeong.core.global.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomCodeGenerator {

    private RandomCodeGenerator() {
    }

    public static String generateCode() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
    }
}
