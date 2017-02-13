package com.dx.test;

import java.lang.reflect.Field;

import static java.lang.System.exit;

/**
 * Created by dx on 2017/2/10.
 */
public class Text2 {
    public static int a;
    public static int b;
    public static void main(String[] args) throws Exception {
        int a = 10;
        int b = 20;
        method( a, b);
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }

    private static void method(Integer a, Integer b) throws NoSuchFieldException, IllegalAccessException {
        Integer i = 1;
        Field field = a.getClass().getDeclaredField("valus");
        field.setAccessible(true);
        field.set(a, 100);
        field.set(b, 100);
    }
    private static void method(int a, int b) {
        a = 100;
        b = 200;
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        exit(0);
    }
}
