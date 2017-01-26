package com.dx.test;

public abstract class Extends {
	abstract void load();
	protected void abc(){
		System.out.println("extends 的 abc 方法  ");
	}
	public static void main(String[] args) {
		Extends e1 = new Ext1();
		Extends e2 = new Ext2();
		e1.load();
		e2.load();
	}
}
class Ext1 extends Extends{
	public void load(){
		System.out.println("Ext1 的load 方法");
		abc();
	}
}
class Ext2 extends Extends{
	void load(){
		System.out.println("Ext2 的load 方法");
		abc();
	}
}
