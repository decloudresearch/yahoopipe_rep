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
		sql = "SELECT * FROM service where SERVICEID=?";
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
	public int findService1(String serviceId){
		sql = "SELECT * FROM service1 where SERVICEID=?";
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
	//向数据库中插入该id和json形式字符串；
	public Boolean addServiceIdContent(Service service){
		if(findService(service.getServiceId())==1){
			return false;
		}else{
			sql = "INSERT INTO service1(SERVICEID,SERVICECONTENT)VALUES(?,?)";
			conn = DBUtils.getConnection();
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, service.getServiceId());
				ps.setString(2, service.getServiceContent());
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
	public Service getService(int i){
		sql = "SELECT PIPEID,PIPECONTENT FROM pipejson where ID=?";
		conn = DBUtils.getConnection();
		Service service = new Service();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, i);
			rs = ps.executeQuery();
			if(rs.next()){
				service.setServiceId(rs.getString("PIPEID"));
				service.setServiceContent(rs.getString("PIPECONTENT"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.free(rs, ps, conn);
		}
		return service;
	}
	
	public boolean addService(Service service){
		sql = "INSERT INTO yahoopipes(PIPEID,PIPECONTENT)VALUES(?,?)";
		conn = DBUtils.getConnection();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, service.getServiceId());
			ps.setString(2, service.getServiceContent());
			if(ps.executeUpdate() !=1){
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.free(rs, ps, conn);
		}
		return true;
	}
	
	public boolean addServiceForTry(Service service){
		sql = "INSERT INTO yahoopipes_try(PIPEID,PIPECONTENT)VALUES(?,?)";
		conn = DBUtils.getConnection();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, service.getServiceId());
			ps.setString(2, service.getServiceContent());
			if(ps.executeUpdate() !=1){
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.free(rs, ps, conn);
		}
		return true;
	}

	public String findServiceFromPipe(String serviceId){
		sql = "SELECT SERVICECONTENT FROM service where SERVICEID=?";
		conn = DBUtils.getConnection();
		String pipeContent = new String();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, serviceId);
			rs = ps.executeQuery();
			if(rs.next()){
				pipeContent = rs.getString("SERVICECONTENT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.free(rs, ps, conn);
		}
		return pipeContent;
	}
	
	public String findServiceFromPipe2(String serviceId){
		sql = "SELECT SERVICECONTENT FROM service1 where SERVICEID=?";
		conn = DBUtils.getConnection();
		String pipeContent = new String();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, serviceId);
			rs = ps.executeQuery();
			if(rs.next()){
				pipeContent = rs.getString("SERVICECONTENT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.free(rs, ps, conn);
		}
		return pipeContent;
	}
	public String findServiceById(int i){
		sql = "SELECT PIPEID FROM pipe where ID=?";
		conn = DBUtils.getConnection();
		String pipeId = new String();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, i);
			rs = ps.executeQuery();
			if(rs.next()){
				pipeId = rs.getString("PIPEID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.free(rs, ps, conn);
		}
		return pipeId;
	}
	
	public void updatePipe(Service service){
		sql = "UPDATE pipe SET PIPECONTENT = ? where PIPEID = ?";
		conn = DBUtils.getConnection();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, service.getServiceContent());
			ps.setString(2, service.getServiceId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.free(rs, ps, conn);
		}
	}
}
