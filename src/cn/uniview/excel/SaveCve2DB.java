package cn.uniview.excel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import cn.uniview.CVEs.CVEs;
import cn.uniview.Common.Common;
import cn.uniview.JDBC.DbUtil;



public class SaveCve2DB {
	Logger log = Logger.getLogger("Test");
	public void save() throws IOException, SQLException {
		readExcel xlsMain = new readExcel();
		CVEs cves = null;
		List<CVEs> list = xlsMain.readCsv(Common.CSV_PATH);

		for (int i = 0; i < list.size(); i++) {
			cves = list.get(i);
			//System.out.println(Common.SELECT_CVE_SQL +"\"" +cves.getCVE() +"\"");
			List l = DbUtil.selectOne(Common.SELECT_CVE_SQL +"\"" +cves.getCVE() +"\"" , cves);
			if (l.contains(0)) {
				log.info("今日新增"+cves.getCVE()+"漏洞信息");
				DbUtil.insert(Common.INSERT_CVE_SQL, cves);
			} else if(l.contains(1)){
				log.info(("今日更新 " + cves.getCVE() + "漏洞信息"));
				DbUtil.update(Common.INSERT_CVE_SQL, cves);
			}else{
				log.info(cves.getCVE()+"无变化，不更新");
			}
		}
	}
}