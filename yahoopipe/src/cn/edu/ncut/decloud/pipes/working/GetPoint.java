package cn.edu.ncut.decloud.pipes.working;

import java.util.ArrayList;

import cn.edu.ncut.decloud.pipes.wires.Wire;
import cn.edu.ncut.decloud.pipes.wires.WireDao;

public class GetPoint {
	
	public  double GetFinalPoint(){
		double pai = 0.0;
		double sumPoint = 0.0;
		ArrayList<Integer> pointList = new ArrayList<Integer>();
		pointList = getPoint();
		for(int i = 0;i<pointList.size();i++){
			sumPoint += pointList.get(i);
		}
		pai = sumPoint/(pointList.size()*5);
		return pai;
	}
	
	public ArrayList<Integer> getPoint(){
		int k = 5, point = 0;
		WireDao wireDao = new WireDao();
		int countWireCount = wireDao.getTotalCountWireTry();
		ArrayList<Integer> pointList = new ArrayList<Integer>();
		for(int i = 201; i<=400; i++){
			Wire wire = wireDao.findParameterByIdTry(i);
			int result = wireDao.findSrcExist(wire);
			if(result == 1){
				System.out.println("-----srctype is : ----"+wire.getSrcType()+";   "+"----srcurl is ：-----"+wire.getSrcUrl());
				System.out.println("-----targettype is ----: "+wire.getTgtType()+";   "+"----targeturl is ：----"+wire.getTgtUrl());
				ArrayList<Wire> wireListMatch = new ArrayList<Wire>();
				wireListMatch = wireDao.findTargetOrderByCount(wire);
				for(int w = 0;w<wireListMatch.size();w++){
					System.out.println("targettype is :"+wireListMatch.get(w).getTgtType()+";   "+"targeturl is:"+wireListMatch.get(w).getTgtUrl());
				}
				int length = 0;
				if(k>wireListMatch.size()){
					length = wireListMatch.size();
				}else{
					length = k;
				}
				int j = 0;
				for(; j<length; j++){
					if(wire.getTgtType().equals(wireListMatch.get(j).getTgtType()) 
							&& wire.getTgtUrl().equals(wireListMatch.get(j).getTgtUrl())){
						break;
					}
				}
				if(j != length){
					point = k - j;
				}else {
					point = 0;
				}
				pointList.add(point);
				System.out.println("该module推荐得分是："+point);
			}
		}
		return pointList;	
	}
}
