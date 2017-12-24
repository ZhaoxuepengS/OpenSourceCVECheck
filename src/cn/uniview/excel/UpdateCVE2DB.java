package cn.uniview.excel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import cn.uniview.CVEs.CVEs;
import cn.uniview.Common.Common;
import cn.uniview.JDBC.DbUtil;

public class UpdateCVE2DB {
	Logger log = Logger.getLogger("Test");
	public void update() throws IOException, SQLException {
		readExcel xlsMain = new readExcel();
		CVEs cves = null;
		List<CVEs> list = xlsMain.readCsv(Common.CSV_UPDATE_PATH);
		log.info("开始更新本地漏洞库");
		int updateNum = 0;
		int insertNum = 0;
		for (int i = 0; i < list.size(); i++) {
			cves = list.get(i);
			//System.out.println(Common.SELECT_CVE_SQL +"\"" +cves.getCVE() +"\"");
			List l = DbUtil.selectOne(Common.SELECT_CVE_SQL , cves);
			if (l.contains(0)) {
				log.info("今日新增"+cves.getCVE()+"漏洞信息");
				DbUtil.insert(Common.INSERT_CVE_SQL, cves);
				insertNum++;
			} else if(l.contains(1)){
				log.info(("今日更新 " + cves.getCVE() + "漏洞信息"));
				DbUtil.update(Common.UPDATE_CVE_SQL, cves);
				updateNum++;
			}else{
				log.info(cves.getCVE()+"无变化，不更新");
			}
		}
		log.info("共新增漏洞："+insertNum);
		log.info("共更新漏洞信息："+updateNum);
}
}