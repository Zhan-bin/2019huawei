package com.huawei.exe;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import com.huawei.o.Car;
import com.huawei.o.Road;

/**
 * @author danny
 * @create 2019-03-22 10:25
 */
// ���µ�ǰʱ�̵����г�����
public class Road_Dispatch {

	public Road_Dispatch() {
	}

	public void road_dispatch(Road road, int time) throws Exception {
		Car[][] stat = road.getStat();
		boolean perCarIsWait;
		int perCarSub;
		int currSpeed;
		for(int i=0;i<road.getNum();i++){
			perCarIsWait=true;
			perCarSub=-1;
			for(int j=0;j<road.getLen();j++){	
				if(stat[i][j]==null) continue;
				if(stat[i][j]!=null) {	
					
					if(stat[i][j].getTime()>=time+1) {	//ʱ��ΪT+1,ֱ������
						stat[i][j].setWait(false);
						perCarIsWait=false;
						perCarSub=j;
						continue;
					}
					
					else{	//ʱ��ΪT��������ж�
						currSpeed = Math.min(stat[i][j].getSpeed(), road.getSpeed());	//��¼��ǰ������ʻ�ٶ�
						
						if(perCarIsWait){	//ǰ���ǵȴ�״̬
							if(currSpeed>(j-perCarSub-1)){//�ᳬ��ǰ������ȴ�
								stat[i][j].setWait(true);	
								perCarSub=j;
							} 
							else{	//���ᳬ��ǰ��������ʻ
								stat[i][j-currSpeed] = stat[i][j];
								stat[i][j]=null;
								stat[i][j-currSpeed].setWait(false);
								stat[i][j-currSpeed].setTime(time+1);
								perCarIsWait=false;
								perCarSub=j-currSpeed;
							}
						}
						
						else{	//ǰ������̬
							if(currSpeed>(j-perCarSub-1)){//�ᳬ��ǰ��������ʻ��ǰ����
								if(j!=perCarSub+1){
									stat[i][perCarSub+1]=stat[i][j];
									stat[i][j] = null;
								}
									perCarSub++;
									stat[i][perCarSub].setWait(false);
									stat[i][perCarSub].setTime(time+1);
							}
							
							else{	//���ᳬ��ǰ��������ʻ
								stat[i][j-currSpeed] = stat[i][j];
								stat[i][j]=null;
								stat[i][j-currSpeed].setWait(false);
								stat[i][j-currSpeed].setTime(time+1);
								perCarIsWait=false;
								perCarSub=j-currSpeed;
							}
							}
						}		
					}
				}
			}
		}
	}
