package edu.cloud.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.cloud.service.Service;
import edu.cloud.utils.DBUtils;

public class ServiceDao {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = null;
	//查询是否已经存在该id；
	public int findService(String serviceId){
		sql = "SELECT * FROM serviceall where SERVICEID=?";
		conn = DBUtils.getConnection();
		int result = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, serviceId);
			rs = ps.executeQuery();
			if(rs.next()){
				result = 1;
			}else{
				result = 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.free(rs, ps, conn);
		}
		return result;
	}
	//向数据库中插入该id；
	public Boolean addService(Service service){
		if(findService(service.getServiceId())==1){
			return false;
		}else{
			sql = "INSERT INTO serviceall(SERVICEID,SERVICEAUTHOR,SERVICEDATE,SERVICENUM)VALUES(?,?,?,?)";
			conn = DBUtils.getConnection();
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, service.getServiceId());
				ps.setString(2, service.getServiceAuthor());
				ps.setString(3, service.getServiceDate());
				ps.setString(4, service.getServiceNum());
				if(ps.executeUpdate() !=1){
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				DBUtils.free(rs, ps, conn);
			}
		}
		return true;
	}

}
