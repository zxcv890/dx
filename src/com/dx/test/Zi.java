package com.dx.test;

public class Zi extends Fun{
	public static void main(String[] args) {
		Zi zi=new Zi();
		zi.a();
	}
	public void abc(int a ,int b ,int c ){
		System.out.println("子类");
	}
	public void a(){
		abc(1,2,3);
	}
	public void load(){
		System.out.println("Ext1 的load 方法");
		abc();
	}
}
