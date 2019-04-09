package com.huawei.exe;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.huawei.o.Road;

public class Main {
	public static void main(String[] args) throws IOException {
		
		CarFileSplit carFileSplit = new CarFileSplit();	//�������ļ���ʱ���зֳ�С�ļ�
		GenerationGraph graphGenerationGraph = new GenerationGraph();
		graphGenerationGraph.Init("");	//��ʼ��
		Map<String,String> map = graphGenerationGraph.getMap1();	//��·ӳ��
		int[][] graph = graphGenerationGraph.getGraph();	//�ڽӾ���
		Road[][] allRoad = graphGenerationGraph.getRoad(); 	//�洢���е�·����ľ���
		Dijkstra dijkstra = new Dijkstra(graph);// ��ȡ���·��
		List[][] paths = dijkstra.getLists();	//�洢�������·��������
		InitCar inti = InitCar.getInstance();
		
		
	}

}
