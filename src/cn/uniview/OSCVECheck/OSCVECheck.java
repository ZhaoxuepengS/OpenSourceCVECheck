package cn.uniview.OSCVECheck;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.uniview.CVEs.CVEs;
import cn.uniview.Common.Common;
import cn.uniview.Components.Components;
import cn.uniview.JDBC.DbUtil;
import cn.uniview.excel.SaveVulnerability2Csv;

public class OSCVECheck {
	Logger log = Logger.getLogger("Test");
	public void CheckOneOScomponent(Components component) throws SQLException, IOException, InterruptedException{
			log.info("开始搜寻"+component.getDepartment()+component.getcomponentName()+"组件相关CVE：................................................................");
			ArrayList<CVEs> CVEsList = DbUtil.selectCVEByComponentName(Common.SELECT_CVE_BY_COMPONENT_SQL, component.getcomponentName());

			
			String vulnerabilities = "";
			CVEs cve = null;
			SaveVulnerability2Csv saveVulne = new SaveVulnerability2Csv();
			String[] line = {component.getCodeVersion(),component.getDepartment(),component.getcomponentName(),
					component.getcomponentVersion(),component.getcomponentLastestVersion(),"漏洞概要如下：","漏洞明细如下："};
			//代码版本，部门，组件名称，组件当前版本，组件最新版本，漏洞
			saveVulne.save(line);
		if(!CVEsList.isEmpty()){
			Iterator<CVEs> it = CVEsList.iterator();
			while(it.hasNext()){
				//获取该组件全部CVE漏洞
				cve = it.next();
				
				vulnerabilities+=cve.getCVE()+";";
				String cveNo = cve.getCVE();
				String cveDescription = cve.getDescription();
				String cont = cnnvdCrawler(cveNo);
				String[] line2 = {component.getCodeVersion(),component.getDepartment(),component.getcomponentName(),component.getcomponentVersion(),component.getcomponentLastestVersion(),cveNo+"\r\n"+cveDescription,cont};
				saveVulne.save(line2);
			}
		}else{
			log.info("未搜索到"+component.getcomponentName()+"组件相关漏洞");
			String[] line2 = {"","","","","","未找到该组件存在的漏洞"};
			saveVulne.save(line2);
			vulnerabilities = "未搜索到组件漏洞";
		}
		
		DbUtil.updateComponentVulverabilities(Common.UPDATE_CVE_VULNERABILITY,vulnerabilities,component.getID());
}
	public void CheckAllComponents() throws SQLException, IOException, InterruptedException{
		ArrayList<Components> components = DbUtil.selectAllComponent(Common.SELECT_COMPONENT_ALL_SQL);
		Iterator<Components> it = components.iterator();
		Components component= null;
		SaveVulnerability2Csv saveVulne = new SaveVulnerability2Csv();
		String[] line = {"代码主线版本","部门","组件名称","组件当前版本","组件最新版本","漏洞","漏洞cnnvd明细"};
		saveVulne.save(line);
		while(it.hasNext()){
			Thread.sleep(5000);
			component = it.next();
			CheckOneOScomponent(component);
		}
	}
	private String cnnvdCrawler(String cveNo) throws IOException, InterruptedException{
		log.info("开始抓取"+cveNo+"cnnvd漏洞明细信息");
		Document doc = httpPost(cveNo);

		Thread.sleep(5000);
		String halfUrl = doc.select("#vulner_0 > a").attr("href");
		String cnnvdUrl = "http://www.cnnvd.org.cn"+doc.select("#vulner_0 > a").attr("href");
		if("".equals(halfUrl)){
			log.info("未抓取到cnnvd漏洞信息");
			return "没有搜索到cnnvd漏洞信息";
		}
		log.info("搜索到"+cveNo+"cnnvd漏洞信息,正在访问并抓取参数页面");
		Document doc1 = null;
		doc1 = IfUrlError502(cnnvdUrl,doc1);
		if(null==doc1){
			log.info("抓取失败");
			return "获取cnnvd漏洞信息失败";
		}
	
		Elements ele = doc1.select(".d_ldjj");
		
		StringBuffer content =new StringBuffer();

		for (Element element : ele) {
			content.append(element.text());
			content.append("\r\n");
		}

		String cont = content.toString();
		//SaveVulnerability2Csv saveVulne = new SaveVulnerability2Csv(); 
		log.info("抓取成功");
		return cont;
	}
	public Document httpPost(String cve) throws IOException, InterruptedException{
	    String url = "http://www.cnnvd.org.cn/web/vulnerability/queryLds.tag";
        //获取请求连接
	    
        Connection con = Jsoup.connect(url);
        //遍历生成参数
        con.data("CSRFToken=", "");
        con.data("cvHazardRating", "");
        con.data("cvVultype", "");
        con.data("qstartdateXq", "");
        con.data("cvUsedStyle", "");
        con.data("cvCnnvdUpdatedateXq", "");
        con.data("cpvendor", "");
        con.data("relLdKey", "");
        con.data("hotLd", "");
        con.data("isArea", "");
        con.data("qcvCname", "");
        con.data("qcvCnnvdid", cve);
        con.data("qstartdate", "");
        con.data("qenddate", "");
        //插入cookie（头文件形式）
 
		String cookie = "SESSION=0146cedf-c065-4a13-92d1-075a34969993; td_cookie=82749540; td_cookie=72768377; topcookie=a3";
        con.header("Cookie", cookie);
        Document doc=null;
        int i = 1;
        while(i<50){
        try{
        	doc = con.post();
        	break;
        } catch (Exception e) {
        	Thread.sleep(60000);
			System.out.println("出现异常+"+i);
			log.info("出现异常+"+i);
			i++;
        }
        }
     
      
        return doc;

    }
	static Document IfUrlError502(String url, Document doc)
			throws InterruptedException {
		int i=0;
		Connection con = Jsoup.connect(url).ignoreHttpErrors(true);
		while(i<50){
			try {
				i++;
				Response resp = con.execute();
				if(resp.statusCode()==200){
					doc = con.get();
					break;
				}else{
					if(i>49)
						return null;
					Thread.sleep(60000);
					continue;
				}
				
			} catch (Exception e) {
				
			}
		}
		return doc;
	}
}
