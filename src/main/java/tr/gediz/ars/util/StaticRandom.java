package tr.gediz.ars.util;

import java.util.Random;

public class StaticRandom {
    private static Random random = new Random();

    public static Random get() {
        return random;
    }
}
