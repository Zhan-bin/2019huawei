package com.huawei.exe;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.huawei.o.Car;
import com.huawei.o.Road;

public class test3 {
	public static void main(String[] args) throws IOException {
		boolean aa = true;
		if(aa) 
			aa=false;
		else {
			System.out.println("else");
		}
//		BufferedReader br = new BufferedReader(new InputStreamReader(
//				new FileInputStream("E:/test/1-map-training-1/car.txt")));
//		br.readLine();
//		int count =0;
//		while ( br.readLine() != null) count++;
//		System.out.println(count);
		CarFileSplit carFileSplit = new CarFileSplit();
		carFileSplit.splitOfNum(100, "E:/test/1-map-training-1/");
//		List<Integer> list = new ArrayList<>();
//		List<Integer> list1 = new ArrayList<>();
//		list1.add(3);
//		list.add(1);
//		list.add(2);
//		list.remove(1);
//		list.addAll(list1);
//		System.out.println(list.toString());
//		list.clear();
//		System.out.println(list.toString());
		
		
//		Road road = new Road();
//		List<String> list = new ArrayList<String>();
//		list.add("1");
//		list.add("1");
//		Car car =new Car("88", 20, list, 0);
//		road.getStat()[0][0]= car;
//		System.out.println(road.getStat()[0][0].getId());
//		test2 t = new test2();
//		t.gg(road);
//		System.out.println(road.getStat()[0][0]==null);
//		System.out.println(road.getStat()[0][1].getId());
//		System.out.println(road.getStat()[0][1].getTime());
	}

}
