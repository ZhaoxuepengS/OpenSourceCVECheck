package cn.uniview.client;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import cn.uniview.DailyTrack.CVEDailyTrack;
import cn.uniview.Download.CVEDownload;
import cn.uniview.OSCVECheck.OSCVECheck;
import cn.uniview.excel.SaveComponent2DB;
import cn.uniview.excel.SaveCve2DB;
import cn.uniview.excel.UpdateCVE2DB;

public class Client {

	/**
	 * @param args
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	static Logger log = Logger.getLogger("Test");
	public static void main(String[] args) throws IOException, SQLException, InterruptedException {
		// TODO Auto-generated method stub
//		SaveCVE();	
//		SaveComponent();
//		CheckAllComponentCVEs();
//		downloadLastestCVEandUpdate();
//		
		DailyTrack();
	}
	//跟踪每日更新CVE，筛选包含产品引用开源组件的漏洞
	private static void DailyTrack() throws IOException, SQLException {
		CVEDailyTrack cveTrack = new CVEDailyTrack();
		cveTrack.track();
	}
	//下载CVE最新list并更新到数据库表中
	private static void downloadLastestCVEandUpdate() throws IOException,
			SQLException {
		CVEDownload cveDownload = new CVEDownload();
		cveDownload.download();
		UpdateCVE2DB cveUpdate = new UpdateCVE2DB();
		cveUpdate.update();
		log.info("更新完毕");
	}
	//检查并调用爬虫模块，循环抓取所有开源组件存在的漏洞
	private static void CheckAllComponentCVEs() throws SQLException,
			IOException, InterruptedException {
		OSCVECheck osc = new OSCVECheck();
		osc.CheckAllComponents();
	}
	//读取开源组件跟踪表，并添加到数据库开源组件表中
	private static void SaveComponent() throws IOException, SQLException {
		SaveComponent2DB comSave = new SaveComponent2DB();
		comSave.save();
		System.out.println("end");
	}
	//读取全部CVE漏洞库，并添加到数据库cve表中
	private static void SaveCVE() throws IOException, SQLException {
		SaveCve2DB cveSave = new SaveCve2DB();
		cveSave.save();
		System.out.println("end");
	}

}
