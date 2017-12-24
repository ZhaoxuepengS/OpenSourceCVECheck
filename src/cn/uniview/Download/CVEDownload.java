package cn.uniview.Download;

import java.io.IOException;

import org.apache.log4j.Logger;

import cn.uniview.Common.Common;

public class CVEDownload {
	Logger log = Logger.getLogger("Test");
	public void download() throws IOException{
		String url = "http://cve.mitre.org/data/downloads/allitems.csv";
		String path = Common.CSV_UPDATE_PATH;
		String filename = path.substring(path.lastIndexOf("\\"));
		String savePath = path.substring(0,path.lastIndexOf("\\"));
		log.info("开始下载最新漏洞库");
		new DownloadUtil().download(url, filename, savePath);
		log.info("下载完毕");
	}
}
