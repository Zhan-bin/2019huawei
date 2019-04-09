package com.huawei.exe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.huawei.impl.GraphAlgorithmic;
import com.huawei.o.Road;

public class GenerationGraph implements GraphAlgorithmic {
	Road[][] road;
	int[][] graph;
	Map<String, String> map1 = new HashMap<String, String>();
	Map<String, String> map2 = new HashMap<String, String>();
	String[][] cross;  //存储路口信息
	String path="";
	
	//初始化，生成 map，邻接矩阵，cross数组
	public void Init(String path) throws FileNotFoundException{
		int cross_num = 0;
		this.path=path;
		File road_file = new File(path + "road.txt");
		File cross_file = new File(path + "cross.txt");
		BufferedReader cross_br = new BufferedReader(new InputStreamReader(new FileInputStream(cross_file)));
		BufferedReader road_br = new BufferedReader(new InputStreamReader(new FileInputStream(road_file)));

		try {
			while (cross_br.readLine() != null){ //cross_num 包含了第一行的解释，0下标位置不存储数据，从1开始。
				cross_num++;
			}
			cross_br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.road = new Road[cross_num][cross_num];
		this.graph = new int[cross_num][cross_num];
		this.cross= new String[cross_num][4];
		String line;
		String[] str;
		int i;
		int j;

		//初始化道路数组
		try {road_br.readLine();
			while ((line = road_br.readLine()) != null) {
				//System.out.println(line);
				str =line.substring(1,line.length() - 1).split(", ");
				i = Integer.parseInt(str[4]);
				j = Integer.parseInt(str[5]);
				Road r = new Road(Integer.parseInt(str[1]),
						Integer.parseInt(str[3]),
						Integer.parseInt(str[2]),
						i,j);
				road[i][j] = r;
				graph[i][j] = Integer.parseInt(str[1]);
				map1.put(str[4]+"_"+str[5], str[0]);
				map2.put(str[0],str[4]+"_"+str[5]);
				if(Integer.parseInt(str[6])==1){
					//交换顺序
					road[j][i] = new Road(Integer.parseInt(str[1]),
							Integer.parseInt(str[3]),
							Integer.parseInt(str[2]),
							j,i);
					graph[j][i] = Integer.parseInt(str[1]);
					map1.put(str[5]+"_"+str[4], str[0]);
				}
			}
			road_br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public int[][] getGraph() {
		return graph;
	}

	public Map<String, String> getMap1() {
		return this.map1;
	}

	public Road[][] getRoad() {
		return this.road;
	}


	@Override
	public Map<String, String> getMap2() {
		return this.map2;
	}


	public String[][] getCross() throws FileNotFoundException {
		
		BufferedReader cross_br = new BufferedReader(new InputStreamReader(new FileInputStream(this.path + "cross.txt")));
		try {
			cross_br.readLine();
			String str_temp1 = "";
			while ((str_temp1=cross_br.readLine()) != null){ //cross_num 包含了第一行的解释，0下标位置不存储数据，从1开始。
				String[] strs_temp1=str_temp1.substring(1,str_temp1.length()-1).split(", ");
				int crossId = Integer.parseInt(strs_temp1[0]);
				for(int n=1;n<5;n++){
					this.cross[crossId][n-1]=strs_temp1[n];
				}
			}
			cross_br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return cross;
	}


	public void setCross(String[][] cross) {
		this.cross = cross;
	}
	
	
	}