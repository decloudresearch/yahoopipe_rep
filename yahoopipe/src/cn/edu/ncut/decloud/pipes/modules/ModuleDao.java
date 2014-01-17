package cn.edu.ncut.decloud.pipes.modules;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cn.edu.ncut.decloud.pipes.util.DBUtils;

public class ModuleDao {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = null;

	public boolean addModule(Module module) {
		sql = "INSERT INTO pipemodules(PIPEID,MODULEID,TYPE,URLVALUE,BIGTYPE)VALUES(?,?,?,?,?)";
		conn = DBUtils.getConnection();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, module.getPipeId());
			ps.setString(2, module.getId());
			ps.setString(3, module.getType());
			ps.setString(4, module.getUrl());
			ps.setString(5, module.getSourceType());
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

	public Module findModuleType(String pipeId, String moduleId) {
		sql = "SELECT TYPE, URLVALUE FROM pipemodules where PIPEID = ? and MODULEID = ?";
		conn = DBUtils.getConnection();
		Module module = new Module();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, pipeId);
			ps.setString(2, moduleId);
			rs = ps.executeQuery();
			if (rs.next()) {
				module.setType(rs.getString("TYPE"));
				module.setUrl(rs.getString("URLVALUE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
		return module;
	}

	public int findDataSource() {
		sql = "SELECT * FROM yahoomodules";
		conn = DBUtils.getConnection();
		String url = new String();
		int count = 0;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				url = rs.getString("URLVALUE");
				if (url.equals("")) {
					count++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
		return count;
	}

	public ArrayList<Module> findFetchDataSource(String type) {
		sql = "SELECT * FROM yahoomodules WHERE TYPE = ?";
		conn = DBUtils.getConnection();
		ArrayList<Module> moduleList = new ArrayList<Module>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, type);
			rs = ps.executeQuery();
			while (rs.next()) {
				String url = rs.getString("URLVALUE");
				if (!url.equals("") && !url.startsWith("1_URL")) {
					Module fetchModule = new Module();
					fetchModule.setPipeId(rs.getString("PIPEID"));
					fetchModule.setId(rs.getString("MODULEID"));
					fetchModule.setType(rs.getString("TYPE"));
					fetchModule.setUrl(rs.getString("URLVALUE"));
					moduleList.add(fetchModule);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.free(rs, ps, conn);
		}
		return moduleList;
	}

	public ArrayList<String> findMuduleByType(String type) {
		sql = "SELECT PIPEID FROM pipemodules WHERE TYPE = ?";
		conn = DBUtils.getConnection();
		ArrayList<String> pipeId = new ArrayList<String>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, type);
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
}
