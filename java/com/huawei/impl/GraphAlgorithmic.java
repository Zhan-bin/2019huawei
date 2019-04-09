package com.huawei.impl;

import java.io.FileNotFoundException;
import java.util.Map;

import com.huawei.o.Road;


public interface GraphAlgorithmic {
	public int[][] getGraph();
	public Map<String,String> getMap1(); //获取以路口标志的道路与道路id映射
	public Map<String,String> getMap2(); //获取以路口标志的道路与道路id映射
	public Road[][] getRoad();    //获取道路对象

}
