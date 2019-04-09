package com.huawei.exe;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.huawei.o.Car;
import com.huawei.o.Road;

//针对路口的调度操作
public class Dispatch {
	public static int[][] check = // {{左转需要让的直行车道，右转需要让的直行车道，右转需要让的左转车道}，...}
	{ { 2, 3, 1 }, { 3, 2, 0 }, { 1, 0, 3 }, { 0, 1, 2 } };
	boolean isUpdate;
	boolean isUpdate_all;		
	
	public boolean dispatch(Road[] road_in, Road[] road_out, int currentTime,String[][] cross,Map<String,String> map,String path)
			throws Exception {	
		
		isUpdate = true;
		isUpdate_all = false;
		while (isUpdate) {
			isUpdate = false;
			Road_Dispatch rp = new Road_Dispatch();
			int[] sub_temp = new int[2]; // 存返回的终态下标
			boolean nextRoad = false;
			for (int i = 0; i < 4; i++) {// i为当前车道
				// 初始化各道路方向。
				int[] Dir = InitDir(road_in, road_out);
				// 直行：2 左转：1 右转：0
				if (road_in[i] == null)
					continue;
				// 列，在小于列的长度且不用跳转的时候循环
				for (int j = 0; j < road_in[i].getLen() && !nextRoad; j++) {
					for (int k = 0; k < road_in[i].getNum(); k++) {
						//如果当前位置为空则继续寻找下一个位置
						if(road_in[i].getStat()[k][j]==null || !road_in[i].getStat()[k][j].isWait())continue;
						// 按照任务书调度顺序遍历找到第一辆车进行调度。（在车道内已经更新至T+1的忽略）
						int currSpeed = Math.min(						//计算当前在车道内的车速
								road_in[i].getStat()[k][j].getSpeed(),
								road_in[i].getSpeed());
						if (road_in[i].getStat()[k][j] != null			//当前位置不为空
								&& road_in[i].getStat()[k][j].isWait()	//当前位置车辆是等待状态
								&&  currSpeed> j) {						// 车子会出路口
							int dir = road_in[i].getStat()[k][j].getNextDir();	//获取车子下一时刻的转向
							
							//已到达终点，将路径输出，并将车辆从道路中删除---------------------------------
							if(dir == -1) {
								BufferedWriter bufferedWriter = 
										new BufferedWriter(new FileWriter(new File(path+"answer.txt"), true));	
								bufferedWriter.write("("+road_in[i].getStat()[k][j].getId()+
										", "+road_in[i].getStat()[k][j].getStart_time()
										+mapping(road_in[i].getStat()[k][j].getPath(),map)+")\n");
								bufferedWriter.close();
								road_in[i].getStat()[k][j]=null;
								// 道路内更新
								rp.road_dispatch(road_in[i], currentTime);
								// 当前迭代更新了道路情况，记录当前更新情况和这个时刻的更新情况
								this.isUpdate = true;
								this.isUpdate_all = true;
								Dir[i]=-1;
							}
							
							
							int nextSpeed; //进入下移道路的速度

							// 直行------------------------------------
							if (dir == 2) {
								// 当前车道行驶速度大于待进入车道的行驶速度且当前不在路口，则行驶到路口。
								if (j != 0
										&& Math.min(road_in[i].getStat()[k][j]
												.getSpeed(),
												road_out[check[i][2]]
														.getSpeed()) < currSpeed) {
									road_in[i].setStatEle(k, 0,
											road_in[i].getStat()[k][j]);
									road_in[i].setStatEle(k, j, null);
									road_in[i].getStat()[k][0].setWait(false);
									road_in[i].getStat()[k][0]
											.setTime(currentTime + 1);
									// 道路内更新
									rp.road_dispatch(road_in[i], currentTime);
									// 当前迭代更新了道路情况，记录当前更新情况和这个时刻的更新情况
									this.isUpdate = true;
									this.isUpdate_all = true;
									Dir[i]=-1;
									System.out.println("update!");

								} else {
									//计算进入下一道路的行驶速度 nextSpeed
									if (j == 0)
										nextSpeed = Math.min(road_in[i].getStat()[k][j]
												.getSpeed(),
												road_out[check[i][2]]
														.getSpeed());
									else
										nextSpeed = currSpeed;
									//获取下一时刻将要到达的位置
									sub_temp = action(
											road_out[check[i][2]].getStat(),
											nextSpeed-j);

									// 下一车道状态不确定，当前车辆进入等待状态,开始下一个路口的遍历。
									if (sub_temp[0] == -1)
										return this.isUpdate_all;

									// 下一车道无法进入，只能行驶到路口。
									else if (sub_temp[0] == -2) {
										if(j==0){
											road_in[i].getStat()[k][0] //进入终态
													.setWait(false);
											road_in[i].getStat()[k][0]	//时间加1
													.setTime(currentTime + 1);
											this.isUpdate = true;
											this.isUpdate_all = true;
											Dir[i]=-1;
											System.out.println("update!");
										}
										else{
											road_in[i].setStatEle(k, 0,
												road_in[i].getStat()[k][j]);
											road_in[i].setStatEle(k, j, null);
											road_in[i].getStat()[k][0]
												.setWait(false);
											road_in[i].getStat()[k][0]
												.setTime(currentTime + 1);
											// 道路内更新
											rp.road_dispatch(road_in[i],
													currentTime);
											// 当前迭代更新了道路情况，记录当前更新情况和这个时刻的更新情况
											this.isUpdate = true;
											this.isUpdate_all = true;
											Dir[i]=-1;
											System.out.println("update!");
										}
										
										
										//下一道路可以进入，直接行驶车辆
									} else {
										road_out[check[i][2]].setStatEle(
												sub_temp[0], sub_temp[1],
												road_in[i].getStat()[k][j]);
										road_in[i].setStatEle(k, j, null);
										road_out[check[i][2]].getStat()[sub_temp[0]][sub_temp[1]]
												.setWait(false);
										road_out[check[i][2]].getStat()[sub_temp[0]][sub_temp[1]]
												.setTime(currentTime + 1);
										road_out[check[i][2]].getStat()[sub_temp[0]][sub_temp[1]]
												.update(cross, map);

										// 道路内更新
										rp.road_dispatch(road_in[i],
												currentTime);
										// 当前迭代更新了道路情况，记录当前更新情况和这个时刻的更新情况
										this.isUpdate = true;
										this.isUpdate_all = true;
										Dir[i]=-1;
										System.out.println("update!");

									}
								}
							}

							// 左转------------------------------------
							else if (dir == 1) {

								// 当前车道行驶速度大于待进入车道的行驶速度，则行驶到路口
								if (j != 0
										&& Math.min(road_in[i].getStat()[k][j]
												.getSpeed(),
												road_out[check[i][1]]
														.getSpeed()) < currSpeed) {
									road_in[i].setStatEle(k, 0,
											road_in[i].getStat()[k][j]);
									road_in[i].setStatEle(k, j, null);
									road_in[i].getStat()[k][0].setWait(false);
									road_in[i].getStat()[k][0]
											.setTime(currentTime + 1);

									// 道路内更新
									rp.road_dispatch(road_in[i], currentTime);
									// 当前迭代更新了道路情况，记录当前更新情况和这个时刻的更新情况
									isUpdate = true;
									isUpdate_all = true;
									Dir[i]=-1;
									System.out.println("update!");

								} else {

									// 当前车辆需要让路
									if (Dir[check[i][0]] == 2) {
										nextRoad = true;
										Dir[i] = dir;
										break;
									}
									
									//不需要让路，判断下一路口情况决定下一步动作。
									else {
										//判断进入下一道路的行驶速度
										if (j == 0)
											nextSpeed = Math.min(road_in[i].getStat()[k][j]
													.getSpeed(),
													road_out[check[i][1]]
															.getSpeed());
										else
											nextSpeed = currSpeed;
										//获取下一时刻将要到达的位置
										sub_temp = action(
												road_out[check[i][1]].getStat(),
												nextSpeed-j);

										// 下一车道状态不确定，当前车辆进入等待状态,开始下一个路口的遍历。
										if (sub_temp[0] == -1)
											return this.isUpdate_all;

										// 下一车道无法进入，只能行驶到路口。
										else if (sub_temp[0] == -2) {
											if(j==0){
												road_in[i].getStat()[k][0] //进入终态
														.setWait(false);
												road_in[i].getStat()[k][0]	//时间加1
														.setTime(currentTime + 1);
											}
											else{
												road_in[i].setStatEle(k, 0,
													road_in[i].getStat()[k][j]);
												road_in[i].setStatEle(k, j, null);
												road_in[i].getStat()[k][0]
													.setWait(false);
												road_in[i].getStat()[k][0]
													.setTime(currentTime + 1);
												// 道路内更新
												rp.road_dispatch(road_in[i],
														currentTime);
												// 当前迭代更新了道路情况，记录当前更新情况和这个时刻的更新情况
												this.isUpdate = true;
												this.isUpdate_all = true;
												Dir[i]=-1;
											System.out.println("update!");
											}
											
										} 
										
										//下一道路可以进入，直接行驶车辆
										else {
											road_out[check[i][1]].setStatEle(
													sub_temp[0], sub_temp[1],
													road_in[i].getStat()[k][j]);
											road_in[i].setStatEle(k, j, null);
											road_out[check[i][1]].getStat()[sub_temp[0]][sub_temp[1]]
													.setWait(false);
											road_out[check[i][1]].getStat()[sub_temp[0]][sub_temp[1]]
													.setTime(currentTime + 1);
											road_out[check[i][1]].getStat()[sub_temp[0]][sub_temp[1]]
													.update(cross, map);

											// 道路内更新
											rp.road_dispatch(road_in[i],
													currentTime);
											// 当前迭代更新了道路情况，记录当前更新情况和这个时刻的更新情况
											this.isUpdate = true;
											this.isUpdate_all = true;
											Dir[i]=-1;
											System.out.println("update!");
										}
									}
								}
							}
							

							// 右转------------------------------------
							else if (dir == 0) {

								// 当前车道行驶速度小于待进入车道的行驶速度，则行驶到路口
								if (j != 0
										&& Math.min(road_in[i].getStat()[k][j]
												.getSpeed(),
												road_out[check[i][0]]
														.getSpeed()) < currSpeed) {
									road_in[i].setStatEle(k, 0,
											road_in[i].getStat()[k][j]);
									road_in[i].setStatEle(k, j, null);
									road_in[i].getStat()[k][0].setWait(false);
									road_in[i].getStat()[k][0]
											.setTime(currentTime + 1);

									// 道路内更新
									rp.road_dispatch(road_in[i], currentTime);
									// 当前迭代更新了道路情况，记录当前更新情况和这个时刻的更新情况
									this.isUpdate = true;
									this.isUpdate_all = true;
									Dir[i]=-1;
									System.out.println("update!");
								}
								
								
								else {

									// 当前车辆需要让路
									if (Dir[check[i][1]] == 2 || Dir[check[i][2]] == 1) {
										nextRoad = true;
										Dir[i] = dir;
										break;
									}
									
									//当前车辆不需要让路，判断待进入道路情况。
									else {
										//判断进入下一道路的行驶速度
										if (j == 0)
											nextSpeed = Math.min(road_in[i].getStat()[k][j]
													.getSpeed(),
													road_out[check[i][0]]
															.getSpeed());
										else
											nextSpeed = currSpeed;
										//获取下一时刻将要到达的位置
										sub_temp = action(
												road_out[check[i][0]].getStat(),
												nextSpeed-j);
										//road_in[i].getLen()-j-1 为本道路剩余路程
										// 下一车道状态不确定，当前车辆进入等待状态,开始下一个路口的遍历。
										if (sub_temp[0] == -1)
											
											return isUpdate_all;

										// 下一车道无法进入，只能行驶到路口。
										else if (sub_temp[0] == -2) {
											if(j==0){
												road_in[i].getStat()[k][0] //进入终态
														.setWait(false);
												road_in[i].getStat()[k][0]	//时间加1
														.setTime(currentTime + 1);
											}
											else{
												road_in[i].setStatEle(k, 0,
													road_in[i].getStat()[k][j]);
												road_in[i].setStatEle(k, j, null);
												road_in[i].getStat()[k][0]
													.setWait(false);
												road_in[i].getStat()[k][0]
													.setTime(currentTime + 1);
												// 道路内更新
												rp.road_dispatch(road_in[i],
														currentTime);
												// 当前迭代更新了道路情况，记录当前更新情况和这个时刻的更新情况
												this.isUpdate = true;
												this.isUpdate_all = true;
												Dir[i]=-1;
											System.out.println("update!");
											}
										} 
										
										//下一道路可以进入，直接行驶车辆
										else {
											road_out[check[i][0]].setStatEle(
													sub_temp[0], sub_temp[1],
													road_in[i].getStat()[k][j]);
											road_in[i].setStatEle(k, j, null);
											road_out[check[i][0]].getStat()[sub_temp[0]][sub_temp[1]]
													.setWait(false);
											road_out[check[i][0]].getStat()[sub_temp[0]][sub_temp[1]]
													.setTime(currentTime + 1);
											road_out[check[i][0]].getStat()[sub_temp[0]][sub_temp[1]]
													.update(cross, map);

											// 道路内更新
											rp.road_dispatch(road_in[i],
													currentTime);
											// 当前迭代更新了道路情况，记录当前更新情况和这个时刻的更新情况
											this.isUpdate = true;
											this.isUpdate_all = true;
											Dir[i]=-1;
											System.out.println("update!");
										}
									}
								}
							}
						}
					}
				}
				nextRoad = false;
			}
		}
		
		return this.isUpdate_all;
	}
	
	

	// 计算车辆将进入的道路的位置。
	private static int[] action(Car[][] car, int l) {
		// 返回{-1，-1}则表示无法确定能否进入，需要等待。
		// 返回{-2，-2}则表示前面堵住了，候车直接停在路口。
		int l_temp=l;
		int[] sub = { -1, -1 };
		for (int j = 0; j < car.length; j++) {
			// 车经过范围有没有车阻挡，如有则判断是否为等待状态，没有则直接行驶。
			l_temp=l;
			for (int i = car[0].length - 1; i >= 0 && l_temp >= 1; i--, l_temp--) {
				// 当前位置有车
				if (car[j][i] != null) {
					// 是否是等待状态
					if (car[j][i].isWait()) {
						return sub;
					} else {
						if (i == car[0].length - 1 )  { 
							if(j >= car.length-1){	//第三個車道最後一個位置都堵住了
								sub[0] = -2;
								sub[1] = -2;
								return sub;
							}
							else break;

						} else {
							sub[0] = j;
							sub[1] = i + 1;
							return sub;
						}
					}
				}
				// 当前车道没有车阻挡，则行驶至终态。
				if (l_temp == 1 && car[j][i] == null) {
					sub[0] = j;
					sub[1] = i;
					return sub;
				}
			}
		}
		return sub;
	}
	
	//初始化路口所有道路准备出发的车辆的待转方向
	private static int[] InitDir(Road[] road_in, Road[] road_out) {
		int[] Dir = { -1, -1, -1, -1 };
		boolean findDir = false;
		// 遍历四条车道将准备最先出发的车的方向存放于一个数组当中。
		for (int i = 0; i < 4; i++) {// i为当前车道
			if(road_in[i]==null) continue;
			List<Integer> l = new ArrayList<Integer>(); //遍历到第一辆车为等待，则下次不遍历。
			for (int j = 0; j < road_in[i].getLen() && !findDir; j++) {
				for (int k = 0; k < road_in[i].getNum();) {	
					//等待状态且当前位置不为空
					if(road_in[i].getStat()[k][j]==null || !road_in[i].getStat()[k][j].isWait()) {
						k++;
						continue;
					}
					int dir = road_in[i].getStat()[k][j].getNextDir();
					if(dir==-1) {
						k++;
						continue;
					}
					if (j != 0													//不在路口
							&& Math.min(road_in[i].getStat()[k][j].getSpeed(),	//当前速度大于下一车道行驶速度
									road_out[check[i][dir]].getSpeed()) < Math
									.min(road_in[i].getStat()[k][j].getSpeed(),
											road_in[i].getSpeed()))
						l.add(k);
					else {
						Dir[i] = road_in[i].getStat()[k][j].getNextDir();
						findDir = true;
						break;
					}
					k++;
					while (l.contains(k))
						k++;
				}
			}
			findDir = false;
		}
		return Dir;
	}
	
	//将结果映射成道路id
	private String mapping(List<Integer> path,Map<String,String> map) {
		StringBuffer stringBuffer =new StringBuffer();
		for(int i=0;i<path.size()-1;i++) {
			stringBuffer.append(", ");
			stringBuffer.append(map.get(path.get(i)+"_"+path.get(i+1)));
			
		}
		return stringBuffer.toString();
	}
}
