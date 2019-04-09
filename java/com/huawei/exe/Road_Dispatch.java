package com.huawei.exe;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import com.huawei.o.Car;
import com.huawei.o.Road;

/**
 * @author danny
 * @create 2019-03-22 10:25
 */
// 更新当前时刻的所有车辆。
public class Road_Dispatch {

	public Road_Dispatch() {
	}

	public void road_dispatch(Road road, int time) throws Exception {
		Car[][] stat = road.getStat();
		boolean perCarIsWait;
		int perCarSub;
		int currSpeed;
		for(int i=0;i<road.getNum();i++){
			perCarIsWait=true;
			perCarSub=-1;
			for(int j=0;j<road.getLen();j++){	
				if(stat[i][j]==null) continue;
				if(stat[i][j]!=null) {	
					
					if(stat[i][j].getTime()>=time+1) {	//时间为T+1,直接跳过
						stat[i][j].setWait(false);
						perCarIsWait=false;
						perCarSub=j;
						continue;
					}
					
					else{	//时间为T，则进行判断
						currSpeed = Math.min(stat[i][j].getSpeed(), road.getSpeed());	//记录当前车辆行驶速度
						
						if(perCarIsWait){	//前车是等待状态
							if(currSpeed>(j-perCarSub-1)){//会超过前车，则等待
								stat[i][j].setWait(true);	
								perCarSub=j;
							} 
							else{	//不会超过前车，则行驶
								stat[i][j-currSpeed] = stat[i][j];
								stat[i][j]=null;
								stat[i][j-currSpeed].setWait(false);
								stat[i][j-currSpeed].setTime(time+1);
								perCarIsWait=false;
								perCarSub=j-currSpeed;
							}
						}
						
						else{	//前车是终态
							if(currSpeed>(j-perCarSub-1)){//会超过前车，则行驶到前车后
								if(j!=perCarSub+1){
									stat[i][perCarSub+1]=stat[i][j];
									stat[i][j] = null;
								}
									perCarSub++;
									stat[i][perCarSub].setWait(false);
									stat[i][perCarSub].setTime(time+1);
							}
							
							else{	//不会超过前车，则行驶
								stat[i][j-currSpeed] = stat[i][j];
								stat[i][j]=null;
								stat[i][j-currSpeed].setWait(false);
								stat[i][j-currSpeed].setTime(time+1);
								perCarIsWait=false;
								perCarSub=j-currSpeed;
							}
							}
						}		
					}
				}
			}
		}
	}
