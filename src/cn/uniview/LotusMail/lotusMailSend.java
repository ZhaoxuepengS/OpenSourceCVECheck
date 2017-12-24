package cn.uniview.LotusMail;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lotus.domino.Database;
import lotus.domino.Document;
import lotus.domino.NotesFactory;
import lotus.domino.NotesThread;
import lotus.domino.RichTextItem;
import lotus.domino.Session;

public class lotusMailSend extends EclipseAgentBase {

private String sendname="495149700@qq.com";;
private String servername="ml03-ds/uniview";
private String filepatch="mail/zhaoxuepeng.nsf";
//public lotusMailSend(){
//    try {
//         InputStream in = this.getClass().getResourceAsStream("/MailSystem.properties");
//         Properties props = new Properties();
//         props.load(in);
//         this.sendname="495149700@qq.com";
//         this.servername="ml03-ds/uniview";
//         this.filepatch="mail01/z04326.nsf";
//         System.out.println(sendname);
//         System.out.println(servername);
//         System.out.println(filepatch);
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
    
//}

public void sendMail(String text) {
try {
	NotesThread.sinitThread();
	//Session session = this.getSession();
    Session session = NotesFactory.createSession((String)null, (String)null, "Zhao1992.");
    System.out.println("Username: " + session.getUserName());
    System.out.println(sendname);
    System.out.println(servername);
    System.out.println(filepatch);
    Database db = session.getDatabase(servername, filepatch);
    //Database db=session.getDatabase("ml03-ds/uniview","mail//zhaoxuepeng.nsf");
    if(session != null){
	    Document domMail=db.createDocument();  
	    domMail.appendItemValue("Form","Memo");   
	    RichTextItem   body=domMail.createRichTextItem("body"); 
	
	    body.appendText(text);   
	
	    domMail.send(sendname);   
	    System.out.println("done");  
    }else{
    	System.out.println("db null !");
    }
    } catch (Exception e) {
        e.printStackTrace();
        }
    }


}
