package com.huawei.exe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.huawei.o.Car;
import com.huawei.o.Road;

public class ChangePath {
	public static int[][] check = // {{左转需要让的直行车道，右转需要让的直行车道，右转需要让的左转车道}，...}
	{ { 2, 3, 1 }, { 3, 2, 0 }, { 1, 0, 3 }, { 0, 1, 2 } };
	// 待进入道路{{右转，左转，直行}...}
	public boolean change(Road[] road_in, Road[] road_out, List<Integer>[][] paths,
			int cross, Map<String, String> map,String[][] crosss,Map<String,String> map2) {
		List<Integer> noIn = new ArrayList<Integer>();
		int dir;
		for (int i = 0; i < 4; i++) {	//遍历当前路口等待的车辆
			if (road_in[i] == null)
				continue;
			for (int j = 0; j < road_in[i].getLen(); j++) {	
				for (int k = 0; k < road_in[i].getNum(); k++) {
					if(road_in[i].getStat()[k][j]==null) continue;	//当前位置为空
					else{											//当前位置不为空
						if(road_in[i].getStat()[k][j].isWait()){	//当前位置车辆市政等待状态
							dir = road_in[i].getStat()[k][j].getNextDir();	//当前车辆下一次方向
							//获取车子终点
							int end = road_in[i].getStat()[k][j].getPath().get(road_in[i].getStat()[k][j].getPath().size()-1);
							//直行--------------------- 先改左 再改右
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
							
							//左转--------------------- 先改右 再改直行
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
							
							//右转-------------------------------- 先改直行 再左转
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
				temp = map2.get(cross[sub][i]).split("_");	//存储一路口标记的道路id
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
