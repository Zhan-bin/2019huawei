package com.huawei.o;
import java.util.List;
import java.util.Map;

public class Car {
	String id;	//܇�vid
	int speed; // ��������ٶ�
	List<Integer> path; // ��ʻ·��
	int nextDir; // 2��ֱ�� 1����ת 0����ת
	String currRoad; //��ǰ��ʻ�ĵ�·
	String nextRoad; // ��һ����·
	int sub;	//��ǰ����λ��
	int start_time;
	int time; // ��ǰʱ��
	boolean isWait=false; //�Ƿ�ȴ�
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
			this.sub++;													//�����±�
			this.currRoad = map.get(this.path.get(sub-1)+"_"+this.path.get(sub)); //���µ�ǰ���ڵĵ�·
			this.nextRoad = map.get(this.path.get(sub)+"_"+this.path.get(sub+1));	//���¼����������һ����·
			setNextDir(cross[this.path.get(sub)]);		//������һ�ε�ת��
															
		}
		else this.nextDir = -1;											//�������·�͵����յ�
	}
	
	public void update_change(String[][] cross,Map<String,String> map){
		System.out.println(this.path.get(sub)+"_"+this.path.get(sub+1));
		System.out.println(this.sub);
		this.nextRoad = map.get(this.path.get(sub)+"_"+this.path.get(sub+1));	//���¼����������һ����·
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

// ���³�����һ��ת��
	public void setNextDir(String[] cross) {
		int n = 10;
		int r = 10;
		for (int i = 0; i < cross.length; i++) {
			if (this.currRoad.equals(cross[i])) r = i;
			if (this.nextRoad.equals(cross[i])) n = i;
			}
		int subtraction = r - n;
		if (subtraction == -2 || subtraction == 2)	//ֱ��ʱ����ǰ��·��ȥ��һ����·�±�Ĳ�Ϊ2
			this.nextDir = 2;
		else if (subtraction == -1 || subtraction == 3)	//��תʱ����ǰ��·��ȥ��һ����·�±�Ĳ�Ϊ-1����3
			this.nextDir = 1;
		else if (subtraction == 1 || subtraction == -3)	//��תʱ����ǰ��·��ȥ��һ����·�±�Ĳ�Ϊ1����-3
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
