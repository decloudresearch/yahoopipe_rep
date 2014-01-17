package cn.edu.ncut.decloud.pipes.pipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cn.edu.ncut.decloud.pipes.util.DBUtils;

public class PipeDao {

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = null;

	public int getPipeCount() {
		sql = "SELECT COUNT(ID) FROM pipes";
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

	public ArrayList<String> getPipeId() {
		sql = "SELECT PIPEID FROM pipes";
		conn = DBUtils.getConnection();
		ArrayList<String> pipeId = new ArrayList<String>();
		try {
			ps = conn.prepareStatement(sql);
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

	public ArrayList<String> getAllPipeId() {
		sql = "SELECT PIPEID FROM pipes WHERE ID>0 AND ID<=4000";
		conn = DBUtils.getConnection();
		ArrayList<String> allPipeId = new ArrayList<String>();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				allPipeId.add(rs.getString("PIPEID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
		return allPipeId;
	}

	public ArrayList<String> getAllPipeContent() {
		sql = "SELECT PIPEJSONCONTENT FROM pipes WHERE ID>0 AND ID<=4000";
		conn = DBUtils.getConnection();
		ArrayList<String> allPipeContent = new ArrayList<String>();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				allPipeContent.add(rs.getString("PIPEJSONCONTENT"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
		return allPipeContent;
	}

	public String getPipeJsonContent(String pipeId) {
		sql = "SELECT PIPEJSONCONTENT FROM pipes where PIPEID = ?";
		conn = DBUtils.getConnection();
		String pipeJsonContent = new String();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, pipeId);
			rs = ps.executeQuery();
			if (rs.next()) {
				pipeJsonContent = rs.getString("PIPEJSONCONTENT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
		return pipeJsonContent;
	}

	public String getJson(String id) {
		sql = "SELECT PIPEJSONCONTENT FROM pipes where PIPEID = ?";
		conn = DBUtils.getConnection();
		String pipeJsonContent = new String();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				pipeJsonContent = rs.getString("PIPEJSONCONTENT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
		return pipeJsonContent;
	}

	public boolean findPipe(String pipeId) {
		sql = "SELECT * FROM pipes where PIPEID = ?";
		conn = DBUtils.getConnection();
		boolean flag = false;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, pipeId);
			rs = ps.executeQuery();
			if (rs.next()) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
		return flag;
	}

	public void deletePipeById(String id) {
		if (false == findPipe(id)) {
			return;
		}
		sql = "DELETE FROM pipes where PIPEID = ?";
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

	public boolean addPipe(String pipeId) {
		sql = "INSERT INTO deletepipe(PIPEID)VALUES(?)";
		conn = DBUtils.getConnection();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, pipeId);
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
}
