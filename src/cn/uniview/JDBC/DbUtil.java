package cn.uniview.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.uniview.CVEs.CVEs;
import cn.uniview.Common.Common;
import cn.uniview.Components.Components;

public class DbUtil {
	static Logger log = Logger.getLogger("Test");
	public static void insert(String sql, CVEs cves) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Class.forName(Common.DRIVER);
			log.info("数据库初始化成功");
			conn = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			log.info("数据库链接成功");
			ps = conn.prepareStatement(sql);
			ps.setString(1, cves.getCVE());
			ps.setString(2, cves.getStatus());
			ps.setString(3, cves.getDescription());
			boolean flag = ps.execute();
			if(!flag){
				log.info(("Save data : cve = " + cves.getCVE() + " , Status = " + cves.getStatus() + ", Description = " + cves.getDescription() + " succeed!"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	//对比指定cve编号漏洞，如果重复返回2，不一致返回1，不存在返回0
	public static List selectOne(String sql, CVEs cves) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			Class.forName(Common.DRIVER);
			//log.info("数据库初始化成功");
			conn = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			//log.info("数据库链接成功");
			ps = conn.prepareStatement(sql);
			ps.setString(1, cves.getCVE());
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getString("CVE").equals(cves.getCVE()) && rs.getString("Status").equals(cves.getStatus()) && rs.getString("Description").equals(cves.getDescription())){
					list.add(2);
				}else{
					list.add(1);					
				}
			}else{
				list.add(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return list;
	}
	//查询指定编号CVE漏洞，并返回CVE对象
	public static CVEs selectSingleCVE(String sql, String cveNO) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CVEs cve = null;
		try {
			Class.forName(Common.DRIVER);
			//log.info("数据库初始化成功");
			conn = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			//log.info("数据库链接成功");
			ps = conn.prepareStatement(sql);
			ps.setString(1, cveNO);
			rs = ps.executeQuery();
			while(rs.next()){
				cve = new CVEs();
				cve.setCVE(rs.getString("CVE"));
				cve.setStatus(rs.getString("Status"));
				cve.setDescription(rs.getString("Description"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return cve;
	}
	public static ResultSet selectAll(String sql) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName(Common.DRIVER);
			log.info("数据库初始化成功");
			conn = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			log.info("数据库链接成功");
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return rs;
	}
	public static void update(String sql,CVEs cve) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			Class.forName(Common.DRIVER);
			conn = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			log.info("开始执行Update "+cve.getCVE()+"操作。。");
			ps = conn.prepareStatement(sql);
			ps.setString(1, cve.getDescription());
			ps.setString(2, cve.getCVE());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	//插入单个开源组件
	public static void insertComponent(String sql, Components components) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Class.forName(Common.DRIVER);
			log.info("数据库初始化成功");
			conn = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			log.info("数据库链接成功");
			ps = conn.prepareStatement(sql);
			ps.setString(1, components.getCodeVersion());
			ps.setString(2, components.getDepartment());
			ps.setString(3, components.getcomponentName());
			ps.setString(4, components.getcomponentVersion());
			ps.setString(5, components.getcomponentLastestVersion());
			ps.setString(6, components.getcomponentWebsite());
			
			boolean flag = ps.execute();
			if(!flag){
				log.info(("Save data : OpenSource Components = " + components.getcomponentName() + " succeed!"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	//查询全部开源组件
	public static ArrayList<Components> selectAllComponent(String sql) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Components> components = new ArrayList<>();
		try {
			Class.forName(Common.DRIVER);
			log.info("数据库初始化成功");
			conn = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			log.info("数据库链接成功");
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			Components component = null;
			
			while(rs.next()){
				component = new Components();
				component.setCodeVersion(rs.getString("CodeVersion"));
				component.setID(rs.getString("id"));
				component.setDepartment(rs.getString("Department"));
				component.setcomponentName(rs.getString("componentName"));
				component.setcomponentVersion(rs.getString("componentVersion"));
				component.setcomponentLastestVersion(rs.getString("componentLastestVersion"));
				components.add(component);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return components;
	}
	//更新组件表每个组件存在的漏洞
	public static void updateComponentVulverabilities(String sql,String vulnerabilities,String ComponentID) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			Class.forName(Common.DRIVER);
			log.info("数据库初始化成功");
			conn = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			log.info("数据库链接成功");
			log.info("开始执行Update vulnerabilities操作。。");
			ps = conn.prepareStatement(sql);
			ps.setString(1, vulnerabilities);
			ps.setString(2, ComponentID);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	//查询某个开源组件所存在的CVE漏洞
	public static ArrayList selectCVEByComponentName(String sql,String component) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CVEs cve = null;
		ArrayList cveList = new ArrayList();
		try {
			Class.forName(Common.DRIVER);
			log.info("数据库初始化成功");
			conn = DriverManager.getConnection(Common.URL, Common.USERNAME, Common.PASSWORD);
			log.info("数据库链接成功");
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%"+component+"%");
			rs = ps.executeQuery();
			
			while(rs.next()){
				cve = new CVEs();
				cve.setCVE(rs.getString("CVE"));
				cve.setDescription(rs.getString("Description"));
				cveList.add(cve);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return cveList;
	}
}
