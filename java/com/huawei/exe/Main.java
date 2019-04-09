package com.huawei.exe;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.huawei.o.Road;

public class Main {
	public static void main(String[] args) throws IOException {
		
		CarFileSplit carFileSplit = new CarFileSplit();	//将车辆文件按时刻切分成小文件
		GenerationGraph graphGenerationGraph = new GenerationGraph();
		graphGenerationGraph.Init("");	//初始化
		Map<String,String> map = graphGenerationGraph.getMap1();	//道路映射
		int[][] graph = graphGenerationGraph.getGraph();	//邻接矩阵
		Road[][] allRoad = graphGenerationGraph.getRoad(); 	//存储所有道路对象的矩阵
		Dijkstra dijkstra = new Dijkstra(graph);// 获取最短路径
		List[][] paths = dijkstra.getLists();	//存储所有最短路径的数组
		InitCar inti = InitCar.getInstance();
		
		
	}

}
