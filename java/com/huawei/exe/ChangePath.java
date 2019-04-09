package com.huawei.exe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.huawei.o.Car;
import com.huawei.o.Road;

public class ChangePath {
	public static int[][] check = // {{��ת��Ҫ�õ�ֱ�г�������ת��Ҫ�õ�ֱ�г�������ת��Ҫ�õ���ת����}��...}
	{ { 2, 3, 1 }, { 3, 2, 0 }, { 1, 0, 3 }, { 0, 1, 2 } };
	// �������·{{��ת����ת��ֱ��}...}
	public boolean change(Road[] road_in, Road[] road_out, List<Integer>[][] paths,
			int cross, Map<String, String> map,String[][] crosss,Map<String,String> map2) {
		List<Integer> noIn = new ArrayList<Integer>();
		int dir;
		for (int i = 0; i < 4; i++) {	//������ǰ·�ڵȴ��ĳ���
			if (road_in[i] == null)
				continue;
			for (int j = 0; j < road_in[i].getLen(); j++) {	
				for (int k = 0; k < road_in[i].getNum(); k++) {
					if(road_in[i].getStat()[k][j]==null) continue;	//��ǰλ��Ϊ��
					else{											//��ǰλ�ò�Ϊ��
						if(road_in[i].getStat()[k][j].isWait()){	//��ǰλ�ó��������ȴ�״̬
							dir = road_in[i].getStat()[k][j].getNextDir();	//��ǰ������һ�η���
							//��ȡ�����յ�
							int end = road_in[i].getStat()[k][j].getPath().get(road_in[i].getStat()[k][j].getPath().size()-1);
							//ֱ��--------------------- �ȸ��� �ٸ���
							if(dir==2){
								if(road_out[check[i][1]]!=null) {
//									action(i,road_in[i].getStat()[k][j],road_out[check[i][1]],end,paths,crosss,map);
//									return true;
									
									if(cross==(paths[road_out[check[i][1]].getRoadSub()[1]][end].get(1))){
										action2(road_in[i].getStat()[k][j], paths, map, crosss, road_out[check[i][1]], map2, end);
										return true;
									}
									else{
										action(i,road_in[i].getStat()[k][j],road_out[check[i][1]],end,paths,crosss,map);
										return true;
									}
								}
								if(road_out[check[i][0]]!=null) {
//									action(i,road_in[i].getStat()[k][j],road_out[check[i][0]],end,paths,crosss,map);
//									return true;
									
									if(cross==(paths[road_out[check[i][0]].getRoadSub()[1]][end].get(1))){
										action2(road_in[i].getStat()[k][j], paths, map, crosss, road_out[check[i][0]], map2, end);
										return true;
									}
									else{
										action(i,road_in[i].getStat()[k][j],road_out[check[i][0]],end,paths,crosss,map);
										return true;
									}
								}
								else return false;
							}
							
							//��ת--------------------- �ȸ��� �ٸ�ֱ��
							if(dir==1){
								if(road_out[check[i][0]]!=null) {
//									action(i,road_in[i].getStat()[k][j],road_out[check[i][0]],end,paths,crosss,map);
//									return true;
									
									if(cross==(paths[road_out[check[i][0]].getRoadSub()[1]][end].get(1))){
										action2(road_in[i].getStat()[k][j], paths, map, crosss, road_out[check[i][0]], map2, end);
										return true;
									}
									else{
										action(i,road_in[i].getStat()[k][j],road_out[check[i][0]],end,paths,crosss,map);
										return true;
									}
								}
								if(road_out[check[i][2]]!=null) {
//									action(i,road_in[i].getStat()[k][j],road_out[check[i][2]],end,paths,crosss,map);
//									return true;
									
									if(cross==(paths[road_out[check[i][2]].getRoadSub()[1]][end].get(1))){
										action2(road_in[i].getStat()[k][j], paths, map, crosss, road_out[check[i][2]], map2, end);
										return true;
									}
									else{
										action(i,road_in[i].getStat()[k][j],road_out[check[i][2]],end,paths,crosss,map);
										return true;
									}
								}
								else return false;
							}
							
							//��ת-------------------------------- �ȸ�ֱ�� ����ת
							if(dir==0){
								if(road_out[check[i][2]]!=null) {
//									action(i,road_in[i].getStat()[k][j],road_out[check[i][2]],end,paths,crosss,map);
//									return true;
									if(cross==(paths[road_out[check[i][2]].getRoadSub()[1]][end].get(1))){
										action2(road_in[i].getStat()[k][j], paths, map, crosss, road_out[check[i][2]], map2, end);
										return true;
									}
									else{
										action(i,road_in[i].getStat()[k][j],road_out[check[i][2]],end,paths,crosss,map);
										return true;
									}
								}
								if(road_out[check[i][1]]!=null) {
//									action(i,road_in[i].getStat()[k][j],road_out[check[i][1]],end,paths,crosss,map);
//									return true;
									
									if(cross==(paths[road_out[check[i][1]].getRoadSub()[1]][end].get(1))){
										action2(road_in[i].getStat()[k][j], paths, map, crosss, road_out[check[i][1]], map2, end);
										return true;
									}
									else{
										action(i,road_in[i].getStat()[k][j],road_out[check[i][1]],end,paths,crosss,map);
										return true;
									}
								}
								else return false;
							}
						}
					}
				}
			}
		}
		return false;
	}

	private static void action(int i,Car car,Road out,int end,List<Integer>[][] paths,
			String[][] cross,Map<String,String> map){
		System.out.print("[action1]\n"+car.getId()+":"+car.getPath().toString());
		
		while(car.getPath().size()>car.getSub()+1)
			car.getPath().remove(car.getSub()+1);
		car.getPath().addAll(paths[out.getRoadSub()[1]][end]);
		
		System.out.println("->"+car.getPath().toString());
		car.update_change(cross, map);
	}
	
	private static void action2(Car car,List<Integer>[][] paths,Map<String,String> map1,
			String[][] cross,Road road,Map<String,String> map2,int end){
		int sub=road.getRoadSub()[1];
		String[] temp;
		int next;
		for(int i=0;i<4;i++){
			if(!cross[sub][i].equals("-1") && !cross[sub][i].equals(map1.get(road.getRoadSub()[0]+"_"+sub))){
				temp = map2.get(cross[sub][i]).split("_");	//�洢һ·�ڱ�ǵĵ�·id
				if(sub==Integer.parseInt(temp[0]))
					next = Integer.parseInt(temp[1]);
				else
					next = Integer.parseInt(temp[0]);
				if(paths[next][end].get(1)!=sub){
					System.out.print("[action2]\n"+car.getId()+":"+car.getPath().toString());
					while(car.getPath().size()>car.getSub()+1)
						car.getPath().remove(car.getSub()+1);
					car.getPath().add(sub);
					car.getPath().addAll(paths[next][end]);
					System.out.println("->"+car.getPath().toString());
					car.update_change(cross, map1);
					break;
				}
			}
		}
	}
}
