package com.huawei.exe;

import com.huawei.o.Car;
import com.huawei.o.Road;

public class NoCarInRoad {	//判断当前时刻道路上是否还有车
	public boolean isNoCar(Road[][] allRoad){
		for(Road[] roads:allRoad){
			for(Road road:roads){
				if(road==null)continue;
				for(Car[] cars:road.getStat()){
					for(Car car:cars){
						if(car==null)continue;
						else
							return false;
					}
				}
			}
		}
		return true;
	}
}
