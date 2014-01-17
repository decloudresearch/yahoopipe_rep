package cn.edu.ncut.decloud.pipes.wires;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cn.edu.ncut.decloud.pipes.util.DBUtils;

public class WireDao {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = null;

	// 查询是否已经存在该id；
	public int findWire(Wire wire) {
		sql = "SELECT * FROM pipewires where PIPEID = ? and WIREID = ?";
		conn = DBUtils.getConnection();
		int result = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, wire.getPipeId());
			ps.setString(2, wire.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				result = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
		return result;
	}

	public boolean addWire(Wire wire) {
		sql = "INSERT INTO pipewires(PIPEID,WIREID,SRCID,SRCTYPE,SRCURL,TARGETID,TARGETTYPE,TARGETURL)VALUES(?,?,?,?,?,?,?,?)";
		conn = DBUtils.getConnection();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, wire.getPipeId());
			ps.setString(2, wire.getId());
			ps.setString(3, wire.getSrcModuleId());
			ps.setString(4, wire.getSrcType());
			ps.setString(5, wire.getSrcUrl());
			ps.setString(6, wire.getTgtModuleId());
			ps.setString(7, wire.getTgtType());
			ps.setString(8, wire.getTgtUrl());
			if (ps.executeUpdate() != 1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
		return true;
	}

	public ArrayList<Wire> findWireByPipeId(String pipeId) {
		sql = "SELECT * FROM yahoowire where PIPEID = ?";
		conn = DBUtils.getConnection();
		ArrayList<Wire> wireArrayList = new ArrayList<Wire>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, pipeId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Wire wire = new Wire();
				wire.setId(rs.getString("WIREID"));
				wire.setSrcModuleId(rs.getString("SRCID"));
				wire.setSrcType(rs.getString("SRCTYPE"));
				wire.setSrcUrl(rs.getString("SRCURL"));
				wire.setTgtModuleId(rs.getString("TARGETID"));
				wire.setTgtType(rs.getString("TARGETTYPE"));
				wire.setTgtUrl(rs.getString("TARGETURL"));
				wireArrayList.add(wire);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
		return wireArrayList;
	}

	public int calWireCount(Wire wire) {
		sql = "SELECT COUNT(*) FROM yahoowire WHERE SRCTYPE = ? and SRCURL =? and TARGETTYPE = ? and TARGETURL =?";
		conn = DBUtils.getConnection();
		int count = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, wire.getSrcType());
			ps.setString(2, wire.getSrcUrl());
			ps.setString(3, wire.getTgtType());
			ps.setString(4, wire.getTgtUrl());
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
		return count;
	}

	public void updateWire(Wire wire) {
		sql = "UPDATE yahoowire SET COUNT = ? where PIPEID = ? and WIREID = ?";
		conn = DBUtils.getConnection();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, wire.getCount());
			ps.setString(2, wire.getPipeId());
			ps.setString(3, wire.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
	}

	public int getTotalCount() {
		sql = "SELECT COUNT(ID) FROM yahoowire";
		conn = DBUtils.getConnection();
		int count = 0;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
		return count;
	}

	public Wire findParameterById(int i) {
		sql = "SELECT SRCTYPE,SRCURL,TARGETTYPE,TARGETURL,COUNT FROM yahoowire where ID = ?";
		conn = DBUtils.getConnection();
		Wire wire = new Wire();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, i);
			rs = ps.executeQuery();
			if (rs.next()) {
				wire.setSrcType(rs.getString("SRCTYPE"));
				wire.setSrcUrl(rs.getString("SRCURL"));
				wire.setTgtType(rs.getString("TARGETTYPE"));
				wire.setTgtUrl(rs.getString("TARGETURL"));
				wire.setCount(rs.getInt("COUNT"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
		return wire;
	}

	public ArrayList<String> pipeIdCount(Wire wire) {
		sql = "SELECT PIPEID FROM yahoowire WHERE SRCTYPE = ? and SRCURL =? and TARGETTYPE = ? and TARGETURL = ?";
		conn = DBUtils.getConnection();
		ArrayList<String> pipeId = new ArrayList<String>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, wire.getSrcType());
			ps.setString(2, wire.getSrcUrl());
			ps.setString(3, wire.getTgtType());
			ps.setString(4, wire.getTgtUrl());
			rs = ps.executeQuery();
			while (rs.next()) {
				pipeId.add(rs.getString("PIPEID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
		return pipeId;
	}

	public int typeExist(Wire wire) {
		sql = "SELECT * FROM yahoowirecount WHERE SRCTYPE = ? and SRCURL =? and TARGETTYPE = ? and TARGETURL= ?";
		conn = DBUtils.getConnection();
		int result = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, wire.getSrcType());
			ps.setString(2, wire.getSrcUrl());
			ps.setString(3, wire.getTgtType());
			ps.setString(4, wire.getTgtUrl());
			rs = ps.executeQuery();
			if (rs.next()) {
				result = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
		return result;
	}

	public boolean addWireCount(Wire wire) {
		if (findWire(wire) != 1) {
			sql = "INSERT INTO yahoowirecount(PIPEID,SRCTYPE,SRCURL,TARGETTYPE,TARGETURL,COUNT)VALUES(?,?,?,?,?,?)";
			conn = DBUtils.getConnection();
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, wire.getPipeId());
				ps.setString(2, wire.getSrcType());
				ps.setString(3, wire.getSrcUrl());
				ps.setString(4, wire.getTgtType());
				ps.setString(5, wire.getTgtUrl());
				ps.setInt(6, wire.getCount());
				if (ps.executeUpdate() != 1) {
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBUtils.free(rs, ps, conn);
			}
		}
		return true;
	}

	public int findCount(int i) {
		sql = "SELECT * FROM yahoowire WHERE COUNT= ?";
		conn = DBUtils.getConnection();
		int count = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, i);
			rs = ps.executeQuery();
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
		return count;
	}

	public int getTotalCountWireTry() {
		sql = "SELECT COUNT(ID) FROM yahoowire_try";
		conn = DBUtils.getConnection();
		int count = 0;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
		return count;
	}

	public Wire findParameterByIdTry(int i) {
		sql = "SELECT SRCTYPE,SRCURL,TARGETTYPE,TARGETURL FROM yahoowire_try where ID = ?";
		conn = DBUtils.getConnection();
		Wire wire = new Wire();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, i);
			rs = ps.executeQuery();
			if (rs.next()) {
				wire.setSrcType(rs.getString("SRCTYPE"));
				wire.setSrcUrl(rs.getString("SRCURL"));
				wire.setTgtType(rs.getString("TARGETTYPE"));
				wire.setTgtUrl(rs.getString("TARGETURL"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
		return wire;
	}

	public int findSrcExist(Wire wire) {
		sql = "SELECT * FROM yahoowire WHERE SRCTYPE = ? and SRCURL =?";
		conn = DBUtils.getConnection();
		int result = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, wire.getSrcType());
			ps.setString(2, wire.getSrcUrl());
			rs = ps.executeQuery();
			if (rs.next()) {
				result = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
		return result;
	}

	public ArrayList<Wire> findTargetOrderByCount(Wire wireTry) {
		sql = "SELECT * FROM yahoowirecount where SRCTYPE = ? and SRCURL = ? ORDER BY COUNT DESC";
		conn = DBUtils.getConnection();
		ArrayList<Wire> wireArrayList = new ArrayList<Wire>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, wireTry.getSrcType());
			ps.setString(2, wireTry.getSrcUrl());
			rs = ps.executeQuery();
			while (rs.next()) {
				Wire wire1 = new Wire();
				wire1.setSrcType(rs.getString("SRCTYPE"));
				wire1.setSrcUrl(rs.getString("SRCURL"));
				wire1.setTgtType(rs.getString("TARGETTYPE"));
				wire1.setTgtUrl(rs.getString("TARGETURL"));
				wire1.setCount(rs.getInt("COUNT"));
				wireArrayList.add(wire1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
		return wireArrayList;
	}

	public void deleteWireById(String id) {
		sql = "DELETE FROM pipewires where PIPEID = ?";
		conn = DBUtils.getConnection();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
	}

}
