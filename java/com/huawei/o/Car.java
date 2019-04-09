package com.huawei.o;
import java.util.List;
import java.util.Map;

public class Car {
	String id;	//車輛id
	int speed; // 车辆最高速度
	List<Integer> path; // 行驶路径
	int nextDir; // 2：直行 1：左转 0：右转
	String currRoad; //当前行驶的道路
	String nextRoad; // 下一个道路
	int sub;	//當前所在位置
	int start_time;
	int time; // 当前时刻
	boolean isWait=false; //是否等待
	public Car(String id, int speed, List path,int time) {
		this.id = id;
		this.speed = speed;
		this.path = path;
		this.time =time;
		this.start_time=time;
		//System.out.println(path.toString());
		this.nextRoad = path.get(0)+"_"+path.get(1);
		this.sub=0;
	}
	
	public void update(String[][] cross,Map<String,String> map){
		if(path.size()-2>sub){
			this.sub++;													//更新下标
			this.currRoad = map.get(this.path.get(sub-1)+"_"+this.path.get(sub)); //更新当前所在的道路
			this.nextRoad = map.get(this.path.get(sub)+"_"+this.path.get(sub+1));	//更新即将进入的下一个道路
			setNextDir(cross[this.path.get(sub)]);		//更新下一次的转向
															
		}
		else this.nextDir = -1;											//如果出道路就到了终点
	}
	
	public void update_change(String[][] cross,Map<String,String> map){
		System.out.println(this.path.get(sub)+"_"+this.path.get(sub+1));
		System.out.println(this.sub);
		this.nextRoad = map.get(this.path.get(sub)+"_"+this.path.get(sub+1));	//更新即将进入的下一个道路
		setNextDir(cross[this.path.get(sub)]);
		
	}

//start_time
public int getStart_time() {
		return start_time;}
	public void setStart_time(int start_time) {
		this.start_time = start_time;}

//sub
public int getSub() {
		return sub;}
	public void setSub(int sub) {
		this.sub = sub;}

//NextRoad
	public void setNextRoad(String nextRoad) {
		this.nextRoad = nextRoad;}
	
//id
	public String getId() {
		return id;}
	public void setId(String id) {
		this.id = id;}

//Wait
	public boolean isWait() {
		return isWait;}
	public void setWait(boolean isWait) {
		this.isWait = isWait;}

// 更新车子下一次转向
	public void setNextDir(String[] cross) {
		int n = 10;
		int r = 10;
		for (int i = 0; i < cross.length; i++) {
			if (this.currRoad.equals(cross[i])) r = i;
			if (this.nextRoad.equals(cross[i])) n = i;
			}
		int subtraction = r - n;
		if (subtraction == -2 || subtraction == 2)	//直行时，当前道路减去下一个道路下标的差为2
			this.nextDir = 2;
		else if (subtraction == -1 || subtraction == 3)	//左转时，当前道路减去下一个道路下标的差为-1或者3
			this.nextDir = 1;
		else if (subtraction == 1 || subtraction == -3)	//右转时，当前道路减去下一个道路下标的差为1或者-3
			this.nextDir = 0;
	}

//Speed
	public int getSpeed() {
		return speed;}
	public void setSpeed(int speed) {
		this.speed = speed;}

//Path
	public List<Integer> getPath() {
		return path;}
	public void setPath(List path) {
		this.path = path;}

//NextDir
	public int getNextDir() {
		return nextDir;}
	public String getNextRoad() {
		return nextRoad;}

//Time
	public int getTime() {
		return time;}
	public void setTime(int time) {
		this.time = time;}
	public void addTime(){
		this.time++;
	}

}
