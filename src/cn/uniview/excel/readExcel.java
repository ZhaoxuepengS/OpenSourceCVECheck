package cn.uniview.excel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.uniview.CVEs.CVEs;

public class readExcel {

	private FileInputStream fis = null;
	private InputStreamReader isw = null;
	private BufferedReader br = null;
	public List<CVEs> readCsv(String cvePath) throws IOException {
		final String ENCODE = "UTF-8";
		ArrayList cvelist = new ArrayList();
	    CVEs cves = null;
	    fis = new FileInputStream(cvePath);
	    isw = new InputStreamReader(fis, ENCODE);
        br = new BufferedReader(isw);
        String line = null;
        while((line = br.readLine()) != null){
        	if(line.startsWith("CVE-")){
        		cves = new CVEs();
        		String[] cveDetilecache = line.split(",");
        		ArrayList cveDetile=new ArrayList();
        		int start = 0;
        		int end = 0;
        		boolean flag = false;
        		for (int i = 0; i < cveDetilecache.length; i++) {
					if(cveDetilecache[i].startsWith("\"") && (!cveDetilecache[i].endsWith("\""))){
						start = i;
						flag = true;
					}else if(cveDetilecache[i].endsWith("\"")&&flag){
						if(cveDetilecache[i].endsWith("\"\"\"")){
							
						}else if(cveDetilecache[i].endsWith("\"\"")){
							continue;
						}
						end = i;
						String s="";
						if(start<end){
							for(int j=start;j<=end;j++){
								s += cveDetilecache[j];
							}
						cveDetile.add(s);
						flag=false;
						}
					}else if(!flag){
						cveDetile.add(cveDetilecache[i]);
					}
				}
				cves.setCVE(cveDetile.get(0).toString());
				cves.setStatus(cveDetile.get(1).toString());
				cves.setDescription(cveDetile.get(2).toString());
				cvelist.add(cves);
        	}
        }
        br.close();
        isw.close();
        fis.close();
       
        return cvelist;
	}
}
