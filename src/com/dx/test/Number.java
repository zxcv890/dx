package com.dx.test;

public class Number {
public static void main(String[] args) {
	double[] dnumber={6323123123124232346.12 , 489472385.846 , 5482394654.1 , 484354132 , 45265210.0 , 0.066666454 , 0};
	java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#,###0.00");
	for (int i = 0; i < dnumber.length; i++) {
		System.out.println(df.format(dnumber[i]));
//		System.out.println(new Formatter().format("%4.2f", dnumber[i]).toString());
	}
}
}
