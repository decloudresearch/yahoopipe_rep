package cn.edu.ncut.decloud.pipes.working;

import java.util.ArrayList;

import cn.edu.ncut.decloud.pipes.wires.Wire;
import cn.edu.ncut.decloud.pipes.wires.WireDao;

public class GetWirePipeId {
	
	public GetWirePipeId(int id){		
		WireDao wireDao = new WireDao();
		Wire wire = wireDao.findParameterById(id);
		if(wire.getSrcUrl()==null){
			wire.setSrcUrl("");
		}
		if(wire.getTgtUrl()==null){
			wire.setTgtUrl("");
		}
		wire.setPipeId(getPipeId(wire).toString());
		if(getTypeExist(wire)!=1){
			wireDao.addWireCount(wire);
		}
	}	
	
	public ArrayList<String> getPipeId(Wire wire){
		ArrayList<String> pipeId = new ArrayList<String>();		
		WireDao wireDao = new WireDao();
		pipeId = wireDao.pipeIdCount(wire);
		return pipeId;
	}
	
	public int getTypeExist(Wire wire){
		int existResult = 0;
		WireDao wireDao = new WireDao();
		existResult = wireDao.typeExist(wire);
		return existResult;
	}
}
