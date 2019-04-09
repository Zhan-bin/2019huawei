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
		graphGenerationGraph.Init(path);	//��ʼ��
		Map<String,String> map = graphGenerationGraph.getMap1();	//��·ӳ��
		Map<String,String> map2 = graphGenerationGraph.getMap2();
		String[][] cross = graphGenerationGraph.getCross(); //·�����ӵ�·
		int[][] graph = graphGenerationGraph.getGraph();//�ڽӾ���
		Road[][] allRoad = graphGenerationGraph.getRoad();//��·����
		Dijkstra dijkstra = new Dijkstra(graph);
		List[][] list= dijkstra.getLists();
		Update update =new Update();
		update.update(allRoad, map, list, path,cross,map2);
		
		for(int  i=0; i<allRoad.length ; i++){
			for(int  j=0; j<allRoad.length ; j++){
				if(allRoad[i][j]==null) continue;
				System.out.print(map.get(allRoad[i][j].getRoadSub()[0]+"_"+allRoad[i][j].getRoadSub()[1])+
						"��·("+i+"->"+j+")  ");
				for(int  k=0; k<allRoad[i][j].getStat().length ; k++){
					for(int l = 0;l<allRoad[i][j].getLen();l++){
						if(allRoad[i][j].getStat()[k][l]==null) continue;
						System.out.print("λ�ã�"+k+"_"+l+"  ����id��"+
						allRoad[i][j].getStat()[k][l].getId()+"  ʱ�̣�"+
						allRoad[i][j].getStat()[k][l].getTime()+" "+
						allRoad[i][j].getStat()[k][l].isWait()+"  ���٣�"+
						allRoad[i][j].getStat()[k][l].getSpeed()+
						"  ��·���٣�"+allRoad[i][j].getSpeed()
						+"��·���ȣ�"+allRoad[i][j].getLen()+
						"next:"+allRoad[i][j].getStat()[k][l].getNextRoad()+
								"   ,  ");
					}
				}
				System.out.println();
			}
		}

//		��ӡmap
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



//��ӡ���·������
//bufferedWriter.write(list.length+"");
//for(int i=0;i<list.length;i++){
//	for(int j=0;j<list[0].length;j++){
//		if(list[i][j]!=null){
//			bufferedWriter.write(list[i][j].toString()+"\n");
//			}
//	}
//}
//bufferedWriter.close();













