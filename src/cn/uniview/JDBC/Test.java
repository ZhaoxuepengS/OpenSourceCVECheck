package cn.uniview.JDBC;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.uniview.CVEs.CVEs;
import cn.uniview.Common.Common;
import cn.uniview.Components.Components;
public class Test {

	/**
	 * @param args
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws SQLException, IOException {
		// TODO Auto-generated method stub
		//addCVE();
		//ArrayList rs = DbUtil.selectCVEByComponentName(Common.SELECT_CVE_BY_COMPONENT_SQL, "osip");
		Calendar now = Calendar.getInstance();  
		String fileName = now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH);
		System.out.println(fileName);
	}

	private static void addCVE() {
		CVEs cve = new CVEs();
		cve.setCVE("23");
		cve.setStatus("false");
		cve.setDescription("this is test from java");
		try {
			List l = DbUtil.selectOne(Common.SELECT_CVE_SQL + cve.getCVE(), cve);
			if (!l.contains(1)) {
				DbUtil.insert(Common.INSERT_CVE_SQL, cve);
			} else {
				System.out.println("The Record was Exist : cve = " + cve.getCVE() + " , Status = " + cve.getStatus() + ", Description = " + cve.getDescription() + ", and has been throw away!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			
		}
	}

}
