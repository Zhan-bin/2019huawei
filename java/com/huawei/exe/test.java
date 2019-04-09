package com.huawei.exe;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import com.huawei.o.Car;
import com.huawei.o.Road;

public class test {
	public static void main(String[] args) throws Exception  {
		String path = "E:/test/1-map-training-1/";
		
		GenerationGraph graphGenerationGraph = new GenerationGraph();
		CarFileSplit carFileSplit = new CarFileSplit();
//		carFileSplit.splitOfTime(path);
		graphGenerationGraph.Init(path);	//初始化
		Map<String,String> map = graphGenerationGraph.getMap1();	//道路映射
		Map<String,String> map2 = graphGenerationGraph.getMap2();
		String[][] cross = graphGenerationGraph.getCross(); //路口连接道路
		int[][] graph = graphGenerationGraph.getGraph();//邻接矩阵
		Road[][] allRoad = graphGenerationGraph.getRoad();//道路数组
		Dijkstra dijkstra = new Dijkstra(graph);
		List[][] list= dijkstra.getLists();
		Update update =new Update();
		update.update(allRoad, map, list, path,cross,map2);
		
		for(int  i=0; i<allRoad.length ; i++){
			for(int  j=0; j<allRoad.length ; j++){
				if(allRoad[i][j]==null) continue;
				System.out.print(map.get(allRoad[i][j].getRoadSub()[0]+"_"+allRoad[i][j].getRoadSub()[1])+
						"道路("+i+"->"+j+")  ");
				for(int  k=0; k<allRoad[i][j].getStat().length ; k++){
					for(int l = 0;l<allRoad[i][j].getLen();l++){
						if(allRoad[i][j].getStat()[k][l]==null) continue;
						System.out.print("位置："+k+"_"+l+"  车辆id："+
						allRoad[i][j].getStat()[k][l].getId()+"  时刻："+
						allRoad[i][j].getStat()[k][l].getTime()+" "+
						allRoad[i][j].getStat()[k][l].isWait()+"  车速："+
						allRoad[i][j].getStat()[k][l].getSpeed()+
						"  道路限速："+allRoad[i][j].getSpeed()
						+"道路长度："+allRoad[i][j].getLen()+
						"next:"+allRoad[i][j].getStat()[k][l].getNextRoad()+
								"   ,  ");
					}
				}
				System.out.println();
			}
		}

//		打印map
//		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("E:/m.txt",true)));
//		for(String key:map.keySet()){
//			String val = map.get(key);
//			bufferedWriter.write(key+","+val+"\n");
//		}
//		bufferedWriter.close();
	}
}
		
		
//		for(Road[] roads:allRoad){
//			for(Road road:roads){
//				if(road==null) continue;
//				for(Car[] cars:road.getStat()){
//					for(Car car:cars){
//						if(car==null) continue;
//						System.out.print(car.getId()+" "+car.getNextRoad()+" "+car.isWait()+" "+car.getTime()+" ,");
//					}
//				}
//				System.out.println();
//			}
//		}
//		
//		
//		
//	}
//	
//}



//打印最短路径数组
//bufferedWriter.write(list.length+"");
//for(int i=0;i<list.length;i++){
//	for(int j=0;j<list[0].length;j++){
//		if(list[i][j]!=null){
//			bufferedWriter.write(list[i][j].toString()+"\n");
//			}
//	}
//}
//bufferedWriter.close();













