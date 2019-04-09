package com.huawei.impl;

import java.io.FileNotFoundException;
import java.util.Map;

import com.huawei.o.Road;


public interface GraphAlgorithmic {
	public int[][] getGraph();
	public Map<String,String> getMap1(); //��ȡ��·�ڱ�־�ĵ�·���·idӳ��
	public Map<String,String> getMap2(); //��ȡ��·�ڱ�־�ĵ�·���·idӳ��
	public Road[][] getRoad();    //��ȡ��·����

}
