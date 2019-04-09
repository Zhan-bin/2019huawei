package com.huawei.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.huawei.o.Road;

public class test {
//	public static void main(String[] args) throws IOException {
//		int count = 0;
//		InputStream in = new FileInputStream(new File("E://test.txt"));
//		InputStreamReader inR = new InputStreamReader(in);
//		BufferedReader br = new BufferedReader(inR);
//		while(br.readLine()!=null) count++;
//		System.out.println(count);
//public static void main(String[] args) {
//		
//		String msg = "PerformanceManager(第1个中括号)(Product)(第2个中括号)<[第3个中括号]79~";
//		List<String> list = extractMessageByRegular(msg);
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(i+"-->"+list.get(i));
//		}
	
//  }
//	
//	/**
//	 * 使用正则表达式提取中括号中的内容
//	 * @param msg
//	 * @return 
//	 */
//	public static List<String> extractMessageByRegular(String msg){
//		
//		List<String> list=new ArrayList<String>();
//		Pattern p = Pattern.compile("(\\()[^\\)]+\\)");
//		Matcher m = p.matcher(msg);
//		while(m.find()){
//			list.add(m.group().substring(1, m.group().length()-1));
//		}
//		return list;
//	}
//
//
//}
	
	public static void main(String[] args) {
//		List<Integer> list = new ArrayList<>();
//		list.add(12);
//		Road r = new Road(10,10,10);
//		Road[] rr = new Road[2];
//		rr[0] = null;
//		rr[1] = r;
//		t(rr);
//		System.out.println(rr[1].getLen());
	}
	
	static void t(Road[] r){
		r[1].setLen(222);
	}
	
}
