package com.huawei.exe;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.huawei.o.Car;
 //��ʼ����������Ҫ��ʼ����ʱ����е��á���ʼ����ʱ��ֻ�轫��Ҫ��ʼ�����Ǹ�ʱ�̵ĳ������ļ�·�����뼴�ɣ�ʹ���˵�����
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
