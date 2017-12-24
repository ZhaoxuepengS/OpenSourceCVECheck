package cn.uniview.Download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class DownloadUtil {
	public void download(String url,String filename,String savePath) throws IOException{
		URL Downloadurl = new URL(url);
		InputStream is = Downloadurl.openStream();
		byte[] buff=new byte[1024];
		int len=0;
		//读操作
		File file=new File(savePath);
		if (!file.exists()) {
		file.mkdirs();
	}
		//写操作
		OutputStream os=new FileOutputStream(file.getAbsolutePath()+"\\"+filename);
		//一边读一边写
		while ((len=is.read(buff))!=-1) {
		os.write(buff, 0, len);//把读到写到指定的数组里面
		//释放资源
	
		}
		os.close();
		is.close();
}
}
