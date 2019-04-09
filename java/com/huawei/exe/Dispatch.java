package com.huawei.exe;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.huawei.o.Car;
import com.huawei.o.Road;

//���·�ڵĵ��Ȳ���
public class Dispatch {
	public static int[][] check = // {{��ת��Ҫ�õ�ֱ�г�������ת��Ҫ�õ�ֱ�г�������ת��Ҫ�õ���ת����}��...}
	{ { 2, 3, 1 }, { 3, 2, 0 }, { 1, 0, 3 }, { 0, 1, 2 } };
	boolean isUpdate;
	boolean isUpdate_all;		
	
	public boolean dispatch(Road[] road_in, Road[] road_out, int currentTime,String[][] cross,Map<String,String> map,String path)
			throws Exception {	
		
		isUpdate = true;
		isUpdate_all = false;
		while (isUpdate) {
			isUpdate = false;
			Road_Dispatch rp = new Road_Dispatch();
			int[] sub_temp = new int[2]; // �淵�ص���̬�±�
			boolean nextRoad = false;
			for (int i = 0; i < 4; i++) {// iΪ��ǰ����
				// ��ʼ������·����
				int[] Dir = InitDir(road_in, road_out);
				// ֱ�У�2 ��ת��1 ��ת��0
				if (road_in[i] == null)
					continue;
				// �У���С���еĳ����Ҳ�����ת��ʱ��ѭ��
				for (int j = 0; j < road_in[i].getLen() && !nextRoad; j++) {
					for (int k = 0; k < road_in[i].getNum(); k++) {
						//�����ǰλ��Ϊ�������Ѱ����һ��λ��
						if(road_in[i].getStat()[k][j]==null || !road_in[i].getStat()[k][j].isWait())continue;
						// �������������˳������ҵ���һ�������е��ȡ����ڳ������Ѿ�������T+1�ĺ��ԣ�
						int currSpeed = Math.min(						//���㵱ǰ�ڳ����ڵĳ���
								road_in[i].getStat()[k][j].getSpeed(),
								road_in[i].getSpeed());
						if (road_in[i].getStat()[k][j] != null			//��ǰλ�ò�Ϊ��
								&& road_in[i].getStat()[k][j].isWait()	//��ǰλ�ó����ǵȴ�״̬
								&&  currSpeed> j) {						// ���ӻ��·��
							int dir = road_in[i].getStat()[k][j].getNextDir();	//��ȡ������һʱ�̵�ת��
							
							//�ѵ����յ㣬��·����������������ӵ�·��ɾ��---------------------------------
							if(dir == -1) {
								BufferedWriter bufferedWriter = 
										new BufferedWriter(new FileWriter(new File(path+"answer.txt"), true));	
								bufferedWriter.write("("+road_in[i].getStat()[k][j].getId()+
										", "+road_in[i].getStat()[k][j].getStart_time()
										+mapping(road_in[i].getStat()[k][j].getPath(),map)+")\n");
								bufferedWriter.close();
								road_in[i].getStat()[k][j]=null;
								// ��·�ڸ���
								rp.road_dispatch(road_in[i], currentTime);
								// ��ǰ���������˵�·�������¼��ǰ������������ʱ�̵ĸ������
								this.isUpdate = true;
								this.isUpdate_all = true;
								Dir[i]=-1;
							}
							
							
							int nextSpeed; //�������Ƶ�·���ٶ�

							// ֱ��------------------------------------
							if (dir == 2) {
								// ��ǰ������ʻ�ٶȴ��ڴ����복������ʻ�ٶ��ҵ�ǰ����·�ڣ�����ʻ��·�ڡ�
								if (j != 0
										&& Math.min(road_in[i].getStat()[k][j]
												.getSpeed(),
												road_out[check[i][2]]
														.getSpeed()) < currSpeed) {
									road_in[i].setStatEle(k, 0,
											road_in[i].getStat()[k][j]);
									road_in[i].setStatEle(k, j, null);
									road_in[i].getStat()[k][0].setWait(false);
									road_in[i].getStat()[k][0]
											.setTime(currentTime + 1);
									// ��·�ڸ���
									rp.road_dispatch(road_in[i], currentTime);
									// ��ǰ���������˵�·�������¼��ǰ������������ʱ�̵ĸ������
									this.isUpdate = true;
									this.isUpdate_all = true;
									Dir[i]=-1;
									System.out.println("update!");

								} else {
									//���������һ��·����ʻ�ٶ� nextSpeed
									if (j == 0)
										nextSpeed = Math.min(road_in[i].getStat()[k][j]
												.getSpeed(),
												road_out[check[i][2]]
														.getSpeed());
									else
										nextSpeed = currSpeed;
									//��ȡ��һʱ�̽�Ҫ�����λ��
									sub_temp = action(
											road_out[check[i][2]].getStat(),
											nextSpeed-j);

									// ��һ����״̬��ȷ������ǰ��������ȴ�״̬,��ʼ��һ��·�ڵı�����
									if (sub_temp[0] == -1)
										return this.isUpdate_all;

									// ��һ�����޷����룬ֻ����ʻ��·�ڡ�
									else if (sub_temp[0] == -2) {
										if(j==0){
											road_in[i].getStat()[k][0] //������̬
													.setWait(false);
											road_in[i].getStat()[k][0]	//ʱ���1
													.setTime(currentTime + 1);
											this.isUpdate = true;
											this.isUpdate_all = true;
											Dir[i]=-1;
											System.out.println("update!");
										}
										else{
											road_in[i].setStatEle(k, 0,
												road_in[i].getStat()[k][j]);
											road_in[i].setStatEle(k, j, null);
											road_in[i].getStat()[k][0]
												.setWait(false);
											road_in[i].getStat()[k][0]
												.setTime(currentTime + 1);
											// ��·�ڸ���
											rp.road_dispatch(road_in[i],
													currentTime);
											// ��ǰ���������˵�·�������¼��ǰ������������ʱ�̵ĸ������
											this.isUpdate = true;
											this.isUpdate_all = true;
											Dir[i]=-1;
											System.out.println("update!");
										}
										
										
										//��һ��·���Խ��룬ֱ����ʻ����
									} else {
										road_out[check[i][2]].setStatEle(
												sub_temp[0], sub_temp[1],
												road_in[i].getStat()[k][j]);
										road_in[i].setStatEle(k, j, null);
										road_out[check[i][2]].getStat()[sub_temp[0]][sub_temp[1]]
												.setWait(false);
										road_out[check[i][2]].getStat()[sub_temp[0]][sub_temp[1]]
												.setTime(currentTime + 1);
										road_out[check[i][2]].getStat()[sub_temp[0]][sub_temp[1]]
												.update(cross, map);

										// ��·�ڸ���
										rp.road_dispatch(road_in[i],
												currentTime);
										// ��ǰ���������˵�·�������¼��ǰ������������ʱ�̵ĸ������
										this.isUpdate = true;
										this.isUpdate_all = true;
										Dir[i]=-1;
										System.out.println("update!");

									}
								}
							}

							// ��ת------------------------------------
							else if (dir == 1) {

								// ��ǰ������ʻ�ٶȴ��ڴ����복������ʻ�ٶȣ�����ʻ��·��
								if (j != 0
										&& Math.min(road_in[i].getStat()[k][j]
												.getSpeed(),
												road_out[check[i][1]]
														.getSpeed()) < currSpeed) {
									road_in[i].setStatEle(k, 0,
											road_in[i].getStat()[k][j]);
									road_in[i].setStatEle(k, j, null);
									road_in[i].getStat()[k][0].setWait(false);
									road_in[i].getStat()[k][0]
											.setTime(currentTime + 1);

									// ��·�ڸ���
									rp.road_dispatch(road_in[i], currentTime);
									// ��ǰ���������˵�·�������¼��ǰ������������ʱ�̵ĸ������
									isUpdate = true;
									isUpdate_all = true;
									Dir[i]=-1;
									System.out.println("update!");

								} else {

									// ��ǰ������Ҫ��·
									if (Dir[check[i][0]] == 2) {
										nextRoad = true;
										Dir[i] = dir;
										break;
									}
									
									//����Ҫ��·���ж���һ·�����������һ��������
									else {
										//�жϽ�����һ��·����ʻ�ٶ�
										if (j == 0)
											nextSpeed = Math.min(road_in[i].getStat()[k][j]
													.getSpeed(),
													road_out[check[i][1]]
															.getSpeed());
										else
											nextSpeed = currSpeed;
										//��ȡ��һʱ�̽�Ҫ�����λ��
										sub_temp = action(
												road_out[check[i][1]].getStat(),
												nextSpeed-j);

										// ��һ����״̬��ȷ������ǰ��������ȴ�״̬,��ʼ��һ��·�ڵı�����
										if (sub_temp[0] == -1)
											return this.isUpdate_all;

										// ��һ�����޷����룬ֻ����ʻ��·�ڡ�
										else if (sub_temp[0] == -2) {
											if(j==0){
												road_in[i].getStat()[k][0] //������̬
														.setWait(false);
												road_in[i].getStat()[k][0]	//ʱ���1
														.setTime(currentTime + 1);
											}
											else{
												road_in[i].setStatEle(k, 0,
													road_in[i].getStat()[k][j]);
												road_in[i].setStatEle(k, j, null);
												road_in[i].getStat()[k][0]
													.setWait(false);
												road_in[i].getStat()[k][0]
													.setTime(currentTime + 1);
												// ��·�ڸ���
												rp.road_dispatch(road_in[i],
														currentTime);
												// ��ǰ���������˵�·�������¼��ǰ������������ʱ�̵ĸ������
												this.isUpdate = true;
												this.isUpdate_all = true;
												Dir[i]=-1;
											System.out.println("update!");
											}
											
										} 
										
										//��һ��·���Խ��룬ֱ����ʻ����
										else {
											road_out[check[i][1]].setStatEle(
													sub_temp[0], sub_temp[1],
													road_in[i].getStat()[k][j]);
											road_in[i].setStatEle(k, j, null);
											road_out[check[i][1]].getStat()[sub_temp[0]][sub_temp[1]]
													.setWait(false);
											road_out[check[i][1]].getStat()[sub_temp[0]][sub_temp[1]]
													.setTime(currentTime + 1);
											road_out[check[i][1]].getStat()[sub_temp[0]][sub_temp[1]]
													.update(cross, map);

											// ��·�ڸ���
											rp.road_dispatch(road_in[i],
													currentTime);
											// ��ǰ���������˵�·�������¼��ǰ������������ʱ�̵ĸ������
											this.isUpdate = true;
											this.isUpdate_all = true;
											Dir[i]=-1;
											System.out.println("update!");
										}
									}
								}
							}
							

							// ��ת------------------------------------
							else if (dir == 0) {

								// ��ǰ������ʻ�ٶ�С�ڴ����복������ʻ�ٶȣ�����ʻ��·��
								if (j != 0
										&& Math.min(road_in[i].getStat()[k][j]
												.getSpeed(),
												road_out[check[i][0]]
														.getSpeed()) < currSpeed) {
									road_in[i].setStatEle(k, 0,
											road_in[i].getStat()[k][j]);
									road_in[i].setStatEle(k, j, null);
									road_in[i].getStat()[k][0].setWait(false);
									road_in[i].getStat()[k][0]
											.setTime(currentTime + 1);

									// ��·�ڸ���
									rp.road_dispatch(road_in[i], currentTime);
									// ��ǰ���������˵�·�������¼��ǰ������������ʱ�̵ĸ������
									this.isUpdate = true;
									this.isUpdate_all = true;
									Dir[i]=-1;
									System.out.println("update!");
								}
								
								
								else {

									// ��ǰ������Ҫ��·
									if (Dir[check[i][1]] == 2 || Dir[check[i][2]] == 1) {
										nextRoad = true;
										Dir[i] = dir;
										break;
									}
									
									//��ǰ��������Ҫ��·���жϴ������·�����
									else {
										//�жϽ�����һ��·����ʻ�ٶ�
										if (j == 0)
											nextSpeed = Math.min(road_in[i].getStat()[k][j]
													.getSpeed(),
													road_out[check[i][0]]
															.getSpeed());
										else
											nextSpeed = currSpeed;
										//��ȡ��һʱ�̽�Ҫ�����λ��
										sub_temp = action(
												road_out[check[i][0]].getStat(),
												nextSpeed-j);
										//road_in[i].getLen()-j-1 Ϊ����·ʣ��·��
										// ��һ����״̬��ȷ������ǰ��������ȴ�״̬,��ʼ��һ��·�ڵı�����
										if (sub_temp[0] == -1)
											
											return isUpdate_all;

										// ��һ�����޷����룬ֻ����ʻ��·�ڡ�
										else if (sub_temp[0] == -2) {
											if(j==0){
												road_in[i].getStat()[k][0] //������̬
														.setWait(false);
												road_in[i].getStat()[k][0]	//ʱ���1
														.setTime(currentTime + 1);
											}
											else{
												road_in[i].setStatEle(k, 0,
													road_in[i].getStat()[k][j]);
												road_in[i].setStatEle(k, j, null);
												road_in[i].getStat()[k][0]
													.setWait(false);
												road_in[i].getStat()[k][0]
													.setTime(currentTime + 1);
												// ��·�ڸ���
												rp.road_dispatch(road_in[i],
														currentTime);
												// ��ǰ���������˵�·�������¼��ǰ������������ʱ�̵ĸ������
												this.isUpdate = true;
												this.isUpdate_all = true;
												Dir[i]=-1;
											System.out.println("update!");
											}
										} 
										
										//��һ��·���Խ��룬ֱ����ʻ����
										else {
											road_out[check[i][0]].setStatEle(
													sub_temp[0], sub_temp[1],
													road_in[i].getStat()[k][j]);
											road_in[i].setStatEle(k, j, null);
											road_out[check[i][0]].getStat()[sub_temp[0]][sub_temp[1]]
													.setWait(false);
											road_out[check[i][0]].getStat()[sub_temp[0]][sub_temp[1]]
													.setTime(currentTime + 1);
											road_out[check[i][0]].getStat()[sub_temp[0]][sub_temp[1]]
													.update(cross, map);

											// ��·�ڸ���
											rp.road_dispatch(road_in[i],
													currentTime);
											// ��ǰ���������˵�·�������¼��ǰ������������ʱ�̵ĸ������
											this.isUpdate = true;
											this.isUpdate_all = true;
											Dir[i]=-1;
											System.out.println("update!");
										}
									}
								}
							}
						}
					}
				}
				nextRoad = false;
			}
		}
		
		return this.isUpdate_all;
	}
	
	

	// ���㳵��������ĵ�·��λ�á�
	private static int[] action(Car[][] car, int l) {
		// ����{-1��-1}���ʾ�޷�ȷ���ܷ���룬��Ҫ�ȴ���
		// ����{-2��-2}���ʾǰ���ס�ˣ���ֱ��ͣ��·�ڡ�
		int l_temp=l;
		int[] sub = { -1, -1 };
		for (int j = 0; j < car.length; j++) {
			// ��������Χ��û�г��赲���������ж��Ƿ�Ϊ�ȴ�״̬��û����ֱ����ʻ��
			l_temp=l;
			for (int i = car[0].length - 1; i >= 0 && l_temp >= 1; i--, l_temp--) {
				// ��ǰλ���г�
				if (car[j][i] != null) {
					// �Ƿ��ǵȴ�״̬
					if (car[j][i].isWait()) {
						return sub;
					} else {
						if (i == car[0].length - 1 )  { 
							if(j >= car.length-1){	//������܇������һ��λ�ö���ס��
								sub[0] = -2;
								sub[1] = -2;
								return sub;
							}
							else break;

						} else {
							sub[0] = j;
							sub[1] = i + 1;
							return sub;
						}
					}
				}
				// ��ǰ����û�г��赲������ʻ����̬��
				if (l_temp == 1 && car[j][i] == null) {
					sub[0] = j;
					sub[1] = i;
					return sub;
				}
			}
		}
		return sub;
	}
	
	//��ʼ��·�����е�·׼�������ĳ����Ĵ�ת����
	private static int[] InitDir(Road[] road_in, Road[] road_out) {
		int[] Dir = { -1, -1, -1, -1 };
		boolean findDir = false;
		// ��������������׼�����ȳ����ĳ��ķ�������һ�����鵱�С�
		for (int i = 0; i < 4; i++) {// iΪ��ǰ����
			if(road_in[i]==null) continue;
			List<Integer> l = new ArrayList<Integer>(); //��������һ����Ϊ�ȴ������´β�������
			for (int j = 0; j < road_in[i].getLen() && !findDir; j++) {
				for (int k = 0; k < road_in[i].getNum();) {	
					//�ȴ�״̬�ҵ�ǰλ�ò�Ϊ��
					if(road_in[i].getStat()[k][j]==null || !road_in[i].getStat()[k][j].isWait()) {
						k++;
						continue;
					}
					int dir = road_in[i].getStat()[k][j].getNextDir();
					if(dir==-1) {
						k++;
						continue;
					}
					if (j != 0													//����·��
							&& Math.min(road_in[i].getStat()[k][j].getSpeed(),	//��ǰ�ٶȴ�����һ������ʻ�ٶ�
									road_out[check[i][dir]].getSpeed()) < Math
									.min(road_in[i].getStat()[k][j].getSpeed(),
											road_in[i].getSpeed()))
						l.add(k);
					else {
						Dir[i] = road_in[i].getStat()[k][j].getNextDir();
						findDir = true;
						break;
					}
					k++;
					while (l.contains(k))
						k++;
				}
			}
			findDir = false;
		}
		return Dir;
	}
	
	//�����ӳ��ɵ�·id
	private String mapping(List<Integer> path,Map<String,String> map) {
		StringBuffer stringBuffer =new StringBuffer();
		for(int i=0;i<path.size()-1;i++) {
			stringBuffer.append(", ");
			stringBuffer.append(map.get(path.get(i)+"_"+path.get(i+1)));
			
		}
		return stringBuffer.toString();
	}
}
