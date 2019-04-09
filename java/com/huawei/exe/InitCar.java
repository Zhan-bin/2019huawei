package com.huawei.exe;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.huawei.o.Car;
 //初始化汽车，需要初始化的时候进行调用。初始化的时候只需将需要初始化的那个时刻的车辆的文件路径输入即可（使用了单例）
public class InitCar {
	private static InitCar instance;
	private InitCar() {}
	public static InitCar getInstance() {
		if (instance == null) {
			instance = new InitCar();
		}
		return instance;
	}

	public List<Car> init(int time, String path, List[][] paths)
			throws IOException {
		List<Car> list = new ArrayList<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(path + "/car_" + time + ".txt")));
		String line = "";
		while ((line = br.readLine()) != null) {
			String[] lines = line.substring(1,line.length()-1).split(", ");
			Car car = new Car(lines[0], Integer.parseInt(lines[3]),
					paths[Integer.parseInt(lines[1])][Integer.parseInt(lines[2])],time);
			list.add(car);
		}
		return list;
	}

}
