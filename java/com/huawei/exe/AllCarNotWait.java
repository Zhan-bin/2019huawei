package com.huawei.exe;

import com.huawei.o.Car;
import com.huawei.o.Road;

public class AllCarNotWait {
	public boolean isNotWait(Road[][] allRoad){
		for(Road[] roads:allRoad){
			for(Road road:roads){
				if(road==null)continue;
				for(Car[] cars:road.getStat()){
					for(Car car:cars){
						if(car==null)continue;
						else{
							if(car.isWait())return false;
						}
					}
				}
			}
		}
		return true;
	}

}
