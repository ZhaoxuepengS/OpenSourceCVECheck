package cn.uniview.DailyTrack;

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

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.uniview.CVEs.CVEs;
import cn.uniview.Common.Common;
import cn.uniview.Components.Components;
import cn.uniview.JDBC.DbUtil;
import cn.uniview.excel.readOSComponentsExcel;

public class CVEDailyTrack {
	Logger log = Logger.getLogger("Test");
	//第一段落，包含组件相关内容
	StringBuffer sb1 = new StringBuffer();
	//第二段落，包含与组件无关内容
	StringBuffer sb2 = new StringBuffer();
	public void track() throws IOException, SQLException{
		log.info("开始追踪今日最新CVE漏洞");
		String url = "https://cassandra.cerias.purdue.edu/CVE_changes/today.html";
		ArrayList dailyCVElist = new ArrayList();
		readOSComponentsExcel readCom = new readOSComponentsExcel();
		List<Components> list = readCom.readOSExcel(Common.COMPONENT_PATH);
		
		Document doc = Jsoup.connect(url).ignoreHttpErrors(true).timeout(50000).get();
		log.info("连接到CVE更新网页");
		Elements s = doc.select("a");
		log.info("抓取更新CVE共"+s.size()+"个");
		String cveNO ="";
		CVEs cve = null;
		for (Element element : s) {
			cveNO = "CVE-"+element.text();
			log.info("开始追踪："+cveNO);
			//dailyCVElist.add(DbUtil.selectSingleCVE(Common.SELECT_CVE_SQL, cveNO));
			cve = DbUtil.selectSingleCVE(Common.SELECT_CVE_SQL, cveNO);
			
			Iterator<Components> it = list.iterator();
			String comName = "";
			boolean iscontain = false;
			while(it.hasNext()){
				Components com = it.next();
				comName = com.getcomponentName();
				if((cve.getDescription().toLowerCase()).contains((comName.toLowerCase()))){
					sb1.append("\r\n");
					sb1.append("漏洞相关组件："+com.getDepartment()+":"+comName);
					sb1.append("\r\n");
					sb1.append(cve.getCVE());
					sb1.append("\r\n");
					sb1.append(cve.getDescription());
					sb1.append("\r\n");
					iscontain = true;
				}
			}
			if(!iscontain){
				sb2.append("\r\n");
				sb2.append(cve.getCVE());
				sb2.append("\r\n");
				sb2.append(cve.getDescription());
				sb2.append("\r\n");
			}
		}
		
		
		Calendar now = Calendar.getInstance();  
		String fileName = now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH);
		String path = "F:\\CVE跟踪日报\\"+fileName+".txt";
		File dailyCVEReport = new File(path);
		BufferedWriter bfw = new BufferedWriter(new FileWriter(dailyCVEReport));
		bfw.write("................................................今日组件相关漏洞：....................................................................................");
		bfw.write(sb1.toString());
		bfw.newLine();
		bfw.newLine();
		bfw.newLine();
		bfw.write("................................................今日组件无关漏洞：....................................................................................");
		bfw.write(sb2.toString());
		bfw.flush();
		bfw.close();
		log.info("CVE更新漏洞抓取写入完毕");
	}
	
}
