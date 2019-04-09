package com.huawei.exe;

import com.huawei.o.Car;
import com.huawei.o.Road;

public class test2 {
	public void gg(Road road){
		Car[][] car= road.getStat();
		car[0][0].setId("123456");
		car[0][0].setTime(807874967);
		car[0][1] = car[0][0];
		car[0][0] = null;
	}

}
