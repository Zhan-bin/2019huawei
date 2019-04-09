package com.huawei.exe;
import java.util.List;
import java.util.Map;

import com.huawei.o.Car;
import com.huawei.o.Road;

public class AddCarToRoad {
	public List<Car> add(Road[][] allRoad,List<Car> Car_Ready,String[][] cross,Map<String,String> map){
		String[] sub;	//道路下标
		Road road;	
		int[] address;	//最终存入的位置
		for(int k=0;k<Car_Ready.size();k++){
			Car car = Car_Ready.get(k);
			sub = car.getNextRoad().split("_");	//将下一条路的坐标截取出来
			road= allRoad[Integer.parseInt(sub[0])][Integer.parseInt(sub[1])];
			int[] addre ={0,road.getLen()-Math.min(car.getSpeed(),road.getSpeed())};
			boolean flat = false;
			for(int i=0;i<road.getNum();i++){
				flat = false;
				for(int j=road.getLen()-1;j>=addre[1];j--){
					 if(road.getStat()[i][j]!=null){
						 if(j==road.getLen()-1){
							 break;
						 }
						 else {
							 addre[1]=j+1;
							 addre[0]=i; 
							 flat = true;
							 break;
						 }
					 }
					 if(j==addre[1]) {
						 addre[0] = i;
						 flat = true;
						 break;
					 }
				}
				if(flat) break;
				if(i==road.getNum()-1){
					continue;
				}
			}
			road.setStatEle(addre[0], addre[1], car);
			car.update(cross, map);
			Car_Ready.remove(k);
			k--;
		}
		return Car_Ready;
	}

}
