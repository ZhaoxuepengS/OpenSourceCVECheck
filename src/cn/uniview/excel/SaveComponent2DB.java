package cn.uniview.excel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import cn.uniview.CVEs.CVEs;
import cn.uniview.Common.Common;
import cn.uniview.Components.Components;
import cn.uniview.JDBC.DbUtil;

public class SaveComponent2DB {
	Logger log = Logger.getLogger("Test");
	public void save() throws IOException, SQLException {
		readOSComponentsExcel xlsMain = new readOSComponentsExcel();
		Components component = null;
		List<Components> list = xlsMain.readOSExcel(Common.COMPONENT_PATH);

		for (int i = 0; i < list.size(); i++) {
			component = list.get(i);
			DbUtil.insertComponent(Common.INSERT_COMPONENT_SQL, component);
			
		}
	}
}
