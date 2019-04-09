package com.huawei.exe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.huawei.o.Car;
import com.huawei.o.Road;

//���µ�ǰʱ�̵����г�����
public class Update {
	public void update(Road[][] allRoad, Map<String, String> map, List[][] paths, String path, String[][] cross,Map<String,String> map2)
			throws Exception {
		int m = 0;
		int currentTime = 0; // ��¼��ǰʱ�䣬��1��ʼ
		InitCar initCar = InitCar.getInstance(); // �@��car����
		Dispatch dispatch = new Dispatch();		//·�ڵ�����
		AllCarNotWait allCarNotWait = new AllCarNotWait();	//�ж��ǲ������г����Ѿ�������̬	
		ChangePath changePath = new ChangePath();	//�ı�·����
		AddCarToRoad addCarToRoad = new AddCarToRoad();	//��������
		NoCarInRoad noCarInRoad = new NoCarInRoad();	//�ж�·�ϻ���û�г�
		int fileCount = 0;	//�з��ļ�����
		Road_Dispatch road_dispatch = new Road_Dispatch(); //��·����
		List<Car> cars = initCar.init(1, path, paths);	//��ʼ������������
		cars = addCarToRoad.add(allRoad, cars, cross, map);
		addTime(cars,currentTime+2);
		

		//����ֱ�������������յ�
		while (!noCarInRoad.isNoCar(allRoad)) {
			boolean notWait = false; // �Ƿ������г������ǵȴ�״̬
			boolean cross_update = true; // ��û�г����䶯��
			int h = 1;	//��¼��������
			currentTime++;
			//������г����Ѿ����ǵȴ�״̬�����Ѿ�������һ��ʱ��
			while (!notWait) {
//				List<Integer> noPass = new ArrayList<Integer>();
				cross_update = true;
				
				//��·�л��г��ǵȴ�����״̬ �� �ϴθ���ʱ��·���������˸ı�
				while (!notWait && cross_update) {
					long startTime = System.currentTimeMillis(); // ��ʱ
					cross_update = false;
					// ��·�ڵ���
					try {
						for (Road[] roads2 : allRoad) {
							for (Road road2 : roads2) {
								if (road2 != null)
									road_dispatch.road_dispatch(road2, currentTime);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					// ����·��
//					noPass.clear();
					for (int i = 1; i < cross.length; i++) {
						System.out.println("(��" + h + "�ε���)" + "·�ڣ�" + i+"ʱ�̣�"+currentTime);
						Road[][] road1 = getCrossSeq(cross[i], map, allRoad, i); // ��ȡ·�ڵĵ�·����
						if (dispatch.dispatch(road1[0], road1[1], currentTime, cross, map,path))
							cross_update = true;
//						else
//							noPass.add(i);
					}
					// �ж��ǲ������г����Ѿ�����̬
					notWait = allCarNotWait.isNotWait(allRoad);

					// ��ʱ
					long endTime = System.currentTimeMillis();
					long usedTime = (endTime - startTime);
					System.out.println("--------------------��ǰʱ�̸�����ʱ��" + usedTime + "ms----------------------");
					h++;
				}
				//�����·û�и�����·�ϻ��г��ڵȴ�����Եȴ��ĳ�����·��
				if (!notWait) {
					for (m=1; m < cross.length; m++) {
						Road[][] road1 = getCrossSeq(cross[m], map, allRoad, m); // ��ȡ·�ڵĵ�·����
						 changePath.change(road1[0], road1[1], paths, m, map, cross,map2);
					}
				}
			}
			System.out.println("�Ƿ���������" + !notWait);
			if (fileCount < 9) {
				cars.addAll(initCar.init(currentTime+1, path, paths));
				fileCount++;
			}
				cars = addCarToRoad.add(allRoad, cars, cross, map);  //������·
				addTime(cars,currentTime+2);	//��û����·�ĳ��ŵ���һʱ�̳���
		}
		
		}
				
				
//				for(int  i=0; i<allRoad.length ; i++){
//					for(int  j=0; j<allRoad.length ; j++){
//						if(allRoad[i][j]==null) continue;
//						System.out.print("��·("+i+"->"+j+")  ");
//						for(int  k=0; k<allRoad[i][j].getNum() ; k++){
//							for(int l = 0;l<allRoad[i][j].getLen();l++){
//								if(allRoad[i][j].getStat()[k][l]==null) continue;
//								System.out.print("λ�ã�"+k+"_"+l+"  ����id��"+
//								allRoad[i][j].getStat()[k][l].getId()+"  ʱ�̣�"+
//								allRoad[i][j].getStat()[k][l].getTime()+" "+
//								allRoad[i][j].getStat()[k][l].isWait()+"  ���٣�"+
//								allRoad[i][j].getStat()[k][l].getSpeed()+
//								" ��·���٣�"+allRoad[i][j].getSpeed()+"   ,  ");
//							}
//						}
//						System.out.println();
//					}
//				}
		// ����

		// ��·�ڵ���
//		 try {
//		 for(Road[] roads2:allRoad){
//		 for(Road road2:roads2){
//		 if(road2!=null)road_dispatch.road_dispatch(road2, 1);
//		 }
//		 }
//		 } catch (Exception e) {
//		 e.printStackTrace();
//		 }
//		
//		 int[] cross_test={12,18,15};
//		 for(int i:cross_test){
//			 Road[][] road1 = getCrossSeq(cross[i], map, allRoad, i);
//			 //��ȡ·�ڵĵ�·����
//			 if( dispatch.dispatch(road1[0], road1[1], 1,cross,map,path) );
//		 }

				
				

	// ----------------------------------------------------------------------------------

	// ��ȡ���·�ڵ����е�·��׼��������������·�ڵ��ȡ�
	private static Road[][] getCrossSeq(String[] cross, Map<String, String> map, Road[][] allRoad, int crossId) {
		List<String> in_road = new ArrayList<String>(); // �洢�±��ַ���
		List<String> out_road = new ArrayList<String>(); // �洢�±��ַ���
		Road[] road_in = new Road[4]; // ����·�ڵ�·
		Road[] road_out = new Road[4]; // ��·�ڵ�·
		for (int i = 0; i < allRoad.length; i++) {
			if (allRoad[i][crossId] != null)
				in_road.add(i + "_" + crossId);
		}
		for (int i = 0; i < allRoad[0].length; i++) {
			if (allRoad[crossId][i] != null)
				out_road.add(crossId + "_" + i);
		}
		int row;
		int col;
		String id = "";
		// �M·�ڵĵ�·
		for (int j = 0; j < in_road.size(); j++) {
			id = map.get(in_road.get(j));
			row = Integer.parseInt(in_road.get(j).split("_")[0]);
			col = Integer.parseInt(in_road.get(j).split("_")[1]);
			for (int i = 0; i < 4; i++) {
				if (id.equals(cross[i])) {
					if (i == 0) {
						road_in[3] = allRoad[row][col];
					} else if (i == 3) {
						road_in[0] = allRoad[row][col];
					} else {
						road_in[i] = allRoad[row][col];
					}
				}
			}
		}
		// ��·�ڵĵ�·
		for (int j = 0; j < out_road.size(); j++) {
			id = map.get(out_road.get(j));
			row = Integer.parseInt(out_road.get(j).split("_")[0]);
			col = Integer.parseInt(out_road.get(j).split("_")[1]);
			for (int i = 0; i < 4; i++) {
				if (id.equals(cross[i])) {
					if (i == 0) {
						road_out[3] = allRoad[row][col];
					} else if (i == 3) {
						road_out[0] = allRoad[row][col];
					} else {
						road_out[i] = allRoad[row][col];
					}
				}
			}
		}
		Road[][] res = { road_in, road_out };// res[0]�ǽ����·�ĳ���res[1]�������·�����ߵĳ���
		return res;
	}
	
	//�Եȴ���·�ĳ�����ʱ�̽��и���------------------------------------------------
	private static void addTime(List<Car> cars,int time) {
		if (cars == null)
			return;
		for (Car car : cars) {
			car.setTime(time);
			car.setStart_time(time);
			
		}
	}
}
