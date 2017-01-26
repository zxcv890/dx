package com.dx.test;

public class Split {
	public static void main(String[] args) {
		String a = "321.34|434.3";
		String[] b = a.split("[|]");
		System.out.println(b[0]);
//		for (int i = 0; i < b.length; i++){
//			System.out.println(b[i]);
//		}
	}
}
