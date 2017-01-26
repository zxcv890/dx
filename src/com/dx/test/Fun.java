package com.dx.test;

public class Fun {
	public static void main(String[] args) {
		Fun fu=new Fun();
		fu.abc(1, 2, 3);
		System.out.println("=======");
		Fun e = new Zi();
		e.load();
	}
	public void abc(int a , int b , int c){}
	public void ab(int a){
		System.out.println("Fun类ab方法");
	}
	protected void abc(){
		System.out.println("Fun 的 abc 方法  ");
	}
	void load() {

	}
}
