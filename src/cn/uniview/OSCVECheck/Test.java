package cn.uniview.OSCVECheck;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;




public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException, SQLException {
		// TODO Auto-genera
		
	}
	
  /*
	 public static void sendMail(String mailAdr,String subject,String content){
	        //配置发送邮件的环境属性
	        final Properties props = new Properties();
	        // 表示SMTP发送邮件，需要进行身份验证
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.host", "smtp.qq.com");
	        //smtp登陆的账号、密码 ；需开启smtp登陆
	        props.put("mail.user", "495149700@qq.com");
	        // 访问SMTP服务时需要提供的密码,不是邮箱登陆密码，一般都有独立smtp的登陆密码
	        props.put("mail.password", "pxkfwjivikbnbghf");
	        
	        // 构建授权信息，用于进行SMTP进行身份验证
	        Authenticator authenticator = new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                // 用户名、密码
	                String userName = props.getProperty("mail.user");
	                String password = props.getProperty("mail.password");
	                return new PasswordAuthentication(userName, password);
	            }
	        };
	        
	        // 使用环境属性和授权信息，创建邮件会话
	        Session mailSession = Session.getInstance(props, authenticator);
	        // 创建邮件消息
	        MimeMessage message = new MimeMessage(mailSession);
	        // 设置发件人
	        try{
	            InternetAddress form = new InternetAddress(
	                    props.getProperty("mail.user"));
	            message.setFrom(form);

	            // 设置收件人
	            InternetAddress to = new InternetAddress(mailAdr);
	            message.setRecipient(RecipientType.TO, to);
	            
	            // 设置抄送
	            //InternetAddress cc = new InternetAddress("luo_aaaaa@yeah.net");
	            //message.setRecipient(RecipientType.CC, cc);

	            // 设置密送，其他的收件人不能看到密送的邮件地址
	            //InternetAddress bcc = new InternetAddress("aaaaa@163.com");
	            //message.setRecipient(RecipientType.CC, bcc);
	            
	            // 设置邮件标题
	            message.setSubject(subject);
	            // 设置邮件的内容体
	            message.setContent(content, "text/html;charset=UTF-8");
	            // 发送邮件
	            Transport.send(message);
	        }catch(MessagingException e){
	            e.printStackTrace();
	        }
	    }*/
}
