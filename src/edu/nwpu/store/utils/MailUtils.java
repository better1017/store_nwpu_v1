package edu.nwpu.store.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {

	public static void sendMail(String emailAddress, String emailMsg) throws AddressException, MessagingException {
		// 1.创建一个程序与邮件服务器会话对象 Session

		Properties props = new Properties();
		// 设置发送的协议
		props.setProperty("mail.transport.protocol", "SMTP");

		// 设置发送邮件的服务器：smtp.163.com，smtp.qq.com，smtp.google.com
		props.setProperty("mail.host", "smtp.163.com");
		props.setProperty("mail.smtp.auth", "true");// 指定验证为true

		// 创建验证器
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				// 设置发送人的帐号和密码
				return new PasswordAuthentication("yylin7239@163.com", "370781/7239");
			}
		};

		// 与发送方邮件服务器建立连接
		Session session = Session.getInstance(props, auth);

		// 2.创建一个Message，它相当于是邮件内容
		Message message = new MimeMessage(session);

		// 设置发送者
		message.setFrom(new InternetAddress("yylin7239@163.com"));

		// 设置发送方式与接收者，TO：收件人，CC：抄送，BCC：暗送（密送）。
		message.setRecipient(RecipientType.TO, new InternetAddress(emailAddress));

		// 设置邮件主题
		message.setSubject("账户激活");
		
		// message.setText("这是一封激活邮件，请<a href='#'>点击</a>");
		// store_nwpu_v1/src/edu/nwpu/store/web/servlet/UserServlet.java
		String url = "http://localhost:8080/store_nwpu_v1/UserServlet?method=activeAccount&code=" + emailMsg;
		String content = "<h1>这是一封来自yylin在线书店的账户激活邮件！激活请转到以下链接地址！</h1><h3><a href='" + url + "'>" + url + "</a></h3>"
				+ "<h1>如果您没有进行相关注册请忽略本邮件~</h1>";
		
		// 设置邮件内容
		message.setContent(content, "text/html;charset=utf-8");

		// 3.创建 Transport用于将邮件发送
		Transport.send(message);
	}

	// 测试：向1281102518@qq.com邮箱发送信息，信息内容：abcdefg
	public static void main(String[] args) throws AddressException, MessagingException {
		MailUtils.sendMail("1281102518@qq.com", "abcdefg");
	}
}
