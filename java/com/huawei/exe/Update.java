package com.huawei.exe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.huawei.o.Car;
import com.huawei.o.Road;

//更新当前时刻的所有车辆。
public class Update {
	public void update(Road[][] allRoad, Map<String, String> map, List[][] paths, String path, String[][] cross,Map<String,String> map2)
			throws Exception {
		int m = 0;
		int currentTime = 0; // 记录当前时间，从1开始
		InitCar initCar = InitCar.getInstance(); // 獲得car對象
		Dispatch dispatch = new Dispatch();		//路口调度类
		AllCarNotWait allCarNotWait = new AllCarNotWait();	//判断是不是所有车都已经进入终态	
		ChangePath changePath = new ChangePath();	//改变路径类
		AddCarToRoad addCarToRoad = new AddCarToRoad();	//车辆出发
		NoCarInRoad noCarInRoad = new NoCarInRoad();	//判断路上还有没有车
		int fileCount = 0;	//切分文件个数
		Road_Dispatch road_dispatch = new Road_Dispatch(); //道路调度
		List<Car> cars = initCar.init(1, path, paths);	//初始化待出发车辆
		cars = addCarToRoad.add(allRoad, cars, cross, map);
		addTime(cars,currentTime+2);
		

		//遍历直到车辆都到达终点
		while (!noCarInRoad.isNoCar(allRoad)) {
			boolean notWait = false; // 是否是所有车都不是等待状态
			boolean cross_update = true; // 有没有车辆变动了
			int h = 1;	//记录迭代次数
			currentTime++;
			//如果所有车都已经不是等待状态，就已经更新完一个时刻
			while (!notWait) {
//				List<Integer> noPass = new ArrayList<Integer>();
				cross_update = true;
				
				//道路中还有车是等待调度状态 且 上次更新时道路车况发生了改变
				while (!notWait && cross_update) {
					long startTime = System.currentTimeMillis(); // 计时
					cross_update = false;
					// 道路内调度
					try {
						for (Road[] roads2 : allRoad) {
							for (Road road2 : roads2) {
								if (road2 != null)
									road_dispatch.road_dispatch(road2, currentTime);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					// 调度路口
//					noPass.clear();
					for (int i = 1; i < cross.length; i++) {
						System.out.println("(第" + h + "次迭代)" + "路口：" + i+"时刻："+currentTime);
						Road[][] road1 = getCrossSeq(cross[i], map, allRoad, i); // 获取路口的道路序列
						if (dispatch.dispatch(road1[0], road1[1], currentTime, cross, map,path))
							cross_update = true;
//						else
//							noPass.add(i);
					}
					// 判断是不是所有车都已经是终态
					notWait = allCarNotWait.isNotWait(allRoad);

					// 计时
					long endTime = System.currentTimeMillis();
					long usedTime = (endTime - startTime);
					System.out.println("--------------------当前时刻更新用时：" + usedTime + "ms----------------------");
					h++;
				}
				//如果道路没有更新且路上还有车在等待，则对等待的车更换路径
				if (!notWait) {
					for (m=1; m < cross.length; m++) {
						Road[][] road1 = getCrossSeq(cross[m], map, allRoad, m); // 获取路口的道路序列
						 changePath.change(road1[0], road1[1], paths, m, map, cross,map2);
					}
				}
			}
			System.out.println("是否有死锁：" + !notWait);
			if (fileCount < 9) {
				cars.addAll(initCar.init(currentTime+1, path, paths));
				fileCount++;
			}
				cars = addCarToRoad.add(allRoad, cars, cross, map);  //车辆上路
				addTime(cars,currentTime+2);	//将没有上路的车放到下一时刻出发
		}
		
		}
				
				
//				for(int  i=0; i<allRoad.length ; i++){
//					for(int  j=0; j<allRoad.length ; j++){
//						if(allRoad[i][j]==null) continue;
//						System.out.print("道路("+i+"->"+j+")  ");
//						for(int  k=0; k<allRoad[i][j].getNum() ; k++){
//							for(int l = 0;l<allRoad[i][j].getLen();l++){
//								if(allRoad[i][j].getStat()[k][l]==null) continue;
//								System.out.print("位置："+k+"_"+l+"  车辆id："+
//								allRoad[i][j].getStat()[k][l].getId()+"  时刻："+
//								allRoad[i][j].getStat()[k][l].getTime()+" "+
//								allRoad[i][j].getStat()[k][l].isWait()+"  车速："+
//								allRoad[i][j].getStat()[k][l].getSpeed()+
//								" 道路限速："+allRoad[i][j].getSpeed()+"   ,  ");
//							}
//						}
//						System.out.println();
//					}
//				}
		// 测试

		// 道路内调度
//		 try {
//		 for(Road[] roads2:allRoad){
//		 for(Road road2:roads2){
//		 if(road2!=null)road_dispatch.road_dispatch(road2, 1);
//		 }
//		 }
//		 } catch (Exception e) {
//		 e.printStackTrace();
//		 }
//		
//		 int[] cross_test={12,18,15};
//		 for(int i:cross_test){
//			 Road[][] road1 = getCrossSeq(cross[i], map, allRoad, i);
//			 //获取路口的道路序列
//			 if( dispatch.dispatch(road1[0], road1[1], 1,cross,map,path) );
//		 }

				
				

	// ----------------------------------------------------------------------------------

	// 获取这个路口的所有道路，准备传给接下来的路口调度。
	private static Road[][] getCrossSeq(String[] cross, Map<String, String> map, Road[][] allRoad, int crossId) {
		List<String> in_road = new ArrayList<String>(); // 存储下标字符串
		List<String> out_road = new ArrayList<String>(); // 存储下标字符串
		Road[] road_in = new Road[4]; // 进入路口的路
		Road[] road_out = new Road[4]; // 出路口的路
		for (int i = 0; i < allRoad.length; i++) {
			if (allRoad[i][crossId] != null)
				in_road.add(i + "_" + crossId);
		}
		for (int i = 0; i < allRoad[0].length; i++) {
			if (allRoad[crossId][i] != null)
				out_road.add(crossId + "_" + i);
		}
		int row;
		int col;
		String id = "";
		// 進路口的道路
		for (int j = 0; j < in_road.size(); j++) {
			id = map.get(in_road.get(j));
			row = Integer.parseInt(in_road.get(j).split("_")[0]);
			col = Integer.parseInt(in_road.get(j).split("_")[1]);
			for (int i = 0; i < 4; i++) {
				if (id.equals(cross[i])) {
					if (i == 0) {
						road_in[3] = allRoad[row][col];
					} else if (i == 3) {
						road_in[0] = allRoad[row][col];
					} else {
						road_in[i] = allRoad[row][col];
					}
				}
			}
		}
		// 出路口的道路
		for (int j = 0; j < out_road.size(); j++) {
			id = map.get(out_road.get(j));
			row = Integer.parseInt(out_road.get(j).split("_")[0]);
			col = Integer.parseInt(out_road.get(j).split("_")[1]);
			for (int i = 0; i < 4; i++) {
				if (id.equals(cross[i])) {
					if (i == 0) {
						road_out[3] = allRoad[row][col];
					} else if (i == 3) {
						road_out[0] = allRoad[row][col];
					} else {
						road_out[i] = allRoad[row][col];
					}
				}
			}
		}
		Road[][] res = { road_in, road_out };// res[0]是进入道路的车，res[1]是向出道路方向走的车。
		return res;
	}
	
	//对等待上路的车辆的时刻进行更新------------------------------------------------
	private static void addTime(List<Car> cars,int time) {
		if (cars == null)
			return;
		for (Car car : cars) {
			car.setTime(time);
			car.setStart_time(time);
			
		}
	}
}
