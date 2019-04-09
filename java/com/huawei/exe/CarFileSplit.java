package com.huawei.exe;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class CarFileSplit {
	public void splitOfTime(String path) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(path+"/car.txt")));
		String str = "";
		br.readLine();
		while ((str = br.readLine()) != null) {
//			System.out.println(str);
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
					path+"/car_"
							+ str.substring(1, str.length() - 1).split(", ")[4]
							+ ".txt"), true));
			bw.write(str+"\n");
			bw.close();
	}
		br.close();
	}
	
	public void splitOfNum(int n,String path) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(path+"/car.txt")));
		int count=0;
		int i=1;
		int fileCount=1;
		boolean flat = true;
		while(flat){
			flat=false;
			String str = "";
			br.readLine();
			while ((str = br.readLine()) != null) {
				if(Integer.parseInt(str.substring(1, str.length() - 1).split(", ")[4])==i){
					BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
							path+"/car_"
									+ fileCount
									+ ".txt"), true));
					if(count==n-1) {
						fileCount++;
						count=0;
					}
					flat=true;
					count++;
					bw.write(str+"\n");
					bw.close();
				}
			}
		}
	}
	}

