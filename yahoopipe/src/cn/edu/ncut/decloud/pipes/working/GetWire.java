package cn.edu.ncut.decloud.pipes.working;

import java.util.ArrayList;

import cn.edu.ncut.decloud.pipes.wires.Wire;
import cn.edu.ncut.decloud.pipes.wires.WireDao;

public class GetWire {

	public GetWire(String pipeId) {
		WireDao wireDao = new WireDao();
		ArrayList<Wire> wireArrayList = new ArrayList<Wire>();
		wireArrayList = wireDao.findWireByPipeId(pipeId);
		for (int i = 0; i < wireArrayList.size(); i++) {
			int count = calWireCount(wireArrayList.get(i));
			System.out.println("count is :" + count);
			wireArrayList.get(i).setCount(count);
			wireArrayList.get(i).setPipeId(pipeId);
			wireDao.updateWire(wireArrayList.get(i));
		}
	}

	public int calWireCount(Wire wire) {
		int count = 0;
		WireDao wireDao = new WireDao();
		count = wireDao.calWireCount(wire);
		return count;
	}
}
