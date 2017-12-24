package cn.uniview.Common;

public class Common {
		// connect the database
		public static final String DRIVER = "com.mysql.jdbc.Driver";
		public static final String DB_NAME = "opensource_cve";
		public static final String USERNAME = "root";
		public static final String PASSWORD = "2165809";
		public static final String IP = "127.0.0.1";
		public static final String PORT = "3306";
		public static final String URL = "jdbc:mysql://" + IP + ":" + PORT + "/" + DB_NAME+"?characterEncoding=utf-8";
		
		// common
		public static final String CSV_PATH = "D:\\CVE全部库.csv";
		public static final String COMPONENT_PATH= "E:\\开源组件跟踪\\开源组件使用版本跟踪列表.xlsx";
		public static final String VULNERABLITY_PATH = "E:\\开源组件跟踪\\开源组件漏洞汇总.csv";
		public static final String CSV_UPDATE_PATH = "E:\\download\\updateCVE.csv";
		// CVE sql
		public static final String INSERT_CVE_SQL = "insert into CVEsTable(CVE, Status, Description) values(?, ?, ?)";
		public static final String UPDATE_CVE_SQL = "update CVEsTable set Description= ? where CVE = ? ";
		public static final String SELECT_CVE_ALL_SQL = "select * from CVEsTable";
		public static final String SELECT_CVE_SQL = "select * from CVEsTable where CVE = ?";
		// Component sql
		public static final String INSERT_COMPONENT_SQL = "insert into ComponentsTable(CodeVersion, Department, componentName, " +
				"componentVersion, componentLastestVersion, componentWebsite) value(?,?,?,?,?,?)";
		public static final String SELECT_COMPONENT_ALL_SQL = "select * from ComponentsTable";
		public static final String SELECT_COMPONENT_SQL = "select * from ComponentsTable where id = ?";
		//OSCVETrack sql
		public static final String SELECT_CVE_BY_COMPONENT_SQL = "select CVE,Description from cvestable where Description like ?";
		public static final String UPDATE_CVE_VULNERABILITY = "update componentstable set vulnerabilities = ? where id =?";
		
}
