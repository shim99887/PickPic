package com.web.blog.model.service;

import java.util.Random;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.web.blog.model.MemberDto;

@Service
public class ETCServiceImpl implements ETCService {

	@Autowired
	JavaMailSender emailSender;

	public static final String ePw = createKey();

	private MimeMessage createMessage(String to) throws Exception {
		MimeMessage message = emailSender.createMimeMessage();

		message.addRecipients(RecipientType.TO, to);// 보내는 대상
		message.setSubject("PickPic 인증번호가 도착했습니다.");// 제목

		String msgg = "";
		msgg += "<div style='margin:100px;'>";
		msgg += "<h1> 안녕하세요  PickPic입니다!!! </h1>";
		msgg += "<br>";
		msgg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
		msgg += "<br>";
		msgg += "<p>감사합니다!<p>";
		msgg += "<br>";
		msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
		msgg += "<h3 style='color:blue;'>회원가입 코드입니다.</h3>";
		msgg += "<div style='font-size:130%'>";
		msgg += "CODE : <strong>";
		msgg += ePw + "</strong><div><br/> ";
		msgg += "</div>";
		message.setText(msgg, "utf-8", "html");// 내용
		message.setFrom(new InternetAddress("shim99887@gmail.com", "PickPic"));// 보내는 사람
		return message;
	}

	@Override
	public void sendSimpleMessage(String to) throws Exception {
		MimeMessage message = createMessage(to);

		try {
			emailSender.send(message);
		} catch (MailException es) {
			es.printStackTrace();
			throw new IllegalArgumentException();
		}

	}

	@Override
	public void findPwd(String email) throws Exception {
		MimeMessage message = createMessageForPwd(email);

		try {
			emailSender.send(message);
		} catch (MailException es) {
			es.printStackTrace();
			throw new IllegalArgumentException();
		}

	}

	private MimeMessage createMessageForPwd(String email) throws Exception {
		MemberDto dto = new MemberServiceImpl().findUserInfo(email);
		MimeMessage message = emailSender.createMimeMessage();

		message.addRecipients(RecipientType.TO, email);// 보내는 대상
		message.setSubject("PickPic 인증번호가 도착했습니다.");// 제목

		String msgg = "";
		msgg += "<div style='margin:100px;'>";
		msgg += "<h1> 안녕하세요  PickPic입니다!!! </h1>";
		msgg += "<br>";
		msgg += "<p>비밀번호를 확인해주세요.<p>";
		msgg += "<br>";
		msgg += "<p>감사합니다!<p>";
		msgg += "<br>";
		msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
		msgg += "<h3 style='color:blue;'>비밀번호입니다.</h3>";
		msgg += "<div style='font-size:130%'>";
		msgg += "CODE : <strong>";
		msgg += dto.getPwd() + "</strong><div><br/> ";
		msgg += "</div>";
		message.setText(msgg, "utf-8", "html");// 내용
		message.setFrom(new InternetAddress("shim99887@gmail.com", "PickPic"));// 보내는 사람

		return message;
	}

	// 인증코드 만들기
	public static String createKey() {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();

		for (int i = 0; i < 8; i++) { // 인증코드 8자리
			int index = rnd.nextInt(3); // 0~2 까지 랜덤

			switch (index) {
			case 0:
				key.append((char) ((int) (rnd.nextInt(26)) + 97));
				// a~z (ex. 1+97=98 => (char)98 = 'b')
				break;
			case 1:
				key.append((char) ((int) (rnd.nextInt(26)) + 65));
				// A~Z
				break;
			case 2:
				key.append((rnd.nextInt(10)));
				// 0~9
				break;
			}
		}
		return key.toString();
	}

}