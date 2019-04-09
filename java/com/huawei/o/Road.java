package com.huawei.o;

public class Road {
	private int len;		//��·����
	private int[] roadSub=new int[2];
	private int speed;		//��·����
	private int num;		//��·��Ŀ
	private Car[][] stat; //��·״̬
	public Road(int len,int num,int speed,int row,int col){
		this.len=len;
		this.num=num;
		this.roadSub[0]=row;
		this.roadSub[1]=col;
		this.speed = speed;
		this.stat = new Car[num][len];
	}
	
	//roadSub
	public int[] getRoadSub() {
		return roadSub;
	}
	public void setRoadSub(int[] roadSub) {
		this.roadSub = roadSub;
	}
	
	//stat
	public Car[][] getStat() {
		return stat;
	}
	public void setStat(Car[][] stat) {
		this.stat = stat;
	}
	
	//statele
	public void setStatEle(int row,int col,Car car){
		this.stat[row][col] = car;
	}
	
	//Len
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
	
	//num
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	//speed
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
