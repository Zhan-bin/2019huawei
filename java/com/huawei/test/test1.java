
package com.huawei.test;

public class test1 {
	public static void main(String[] args) {
		for(int i=0;i<3;i++){
			System.out.println("i"+i);
			for(int j=0;j<3;j++){
				if(j==1) break;
				System.out.println("j"+j);
			}
		}
	}
}
