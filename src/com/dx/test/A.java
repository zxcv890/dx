package com.dx.test;

/**
 * Created by dx on 2016/12/26.
 */
public class A {
    public static void main(String[] args){
        B b = null;
        b.set();
        C c = null;
        c.set1();
    }
}
class B{
    static{
        System.out.println(1);
    }
    public static void set(){
        System.out.println("ok");
    }
}
abstract class  C{
    public static void set1(){
        System.out.println("ok1");
    }
}