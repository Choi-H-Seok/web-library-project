package com.company.library.admin.member;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class AdminMemberService {
	final static public int ADMIN_ACOUNT_ALREADY_EXIST = 0;
	final static public int ADMIN_ACOUNT_CREATE_SUCCESS = 1;
	final static public int ADMIN_ACOUNT_CREATE_FAIL = -1;
	
	@Autowired
	AdminMemberDao adminMemberDao;
	
	@Autowired
	JavaMailSenderImpl javaMailSenderImpl;
	
	public int createAccountConfirm(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminMemberService] createAccountConfirm()");
		
		boolean isMember = adminMemberDao.isAdminMember(adminMemberVo.getA_m_id());
		
		if (!isMember) {
			//회원가입 처리
			int result = adminMemberDao.insertAdminAccount(adminMemberVo);
			
			if(result > 0) 
				return ADMIN_ACOUNT_CREATE_SUCCESS;
			else 
				return ADMIN_ACOUNT_CREATE_FAIL;

			
		} else {
			//이미등록된 회원
			return ADMIN_ACOUNT_ALREADY_EXIST;
			
		}
	}

	public AdminMemberVo loginConfirm(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminMemberService] loginConfirm()");
		
		AdminMemberVo loginedAdminMemberVo =
				adminMemberDao.selectAdmin(adminMemberVo);
		
		if (loginedAdminMemberVo != null)
			System.out.println("[AdminMemberService] ADMIN MEMBER LOGIN SUCCESS!");
		else
			System.out.println("[AdminMemberService] ADMIN MEMBER LOGIN FAIL!");
		
		return loginedAdminMemberVo;
	}

	public List<AdminMemberVo> listupAdmin() {
		System.out.println("[AdminMemberService] listupAdmin()");
		
		List<AdminMemberVo> list = adminMemberDao.seletAdmins();
		
		return list;
	}

	public void setAdminApproval(int a_m_no) {
		System.out.println("[AdminMemberService] setAdminApproval()");
		
		int result = adminMemberDao.updateAdminAccount(a_m_no);
		
	}

	public int modifyAccountConfirm(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminMemberService] modifyAccountConfirm()");
		
		return adminMemberDao.updateAdminAccount(adminMemberVo);
	}

	public AdminMemberVo getloginedAdminMemberVo(int a_m_no) {
		System.out.println("[AdminMemberService] getloginedAdminMemberVo()");
		return adminMemberDao.selectAdmin(a_m_no);
	}
	
	public int findPasswordConfirm(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminMemberService] findPasswordConfirm");
		
		AdminMemberVo selectedAdminMemberVo = adminMemberDao.selectAdmin(adminMemberVo.getA_m_id(), adminMemberVo.getA_m_name(), adminMemberVo.getA_m_mail());
		
		int result = 0;
		
		if (selectedAdminMemberVo != null) {
			//등록된 관리자이므로 새 비밀번호 생성
			String newPassword = createPassword();
			
			//데이터베이스에 업데이트
			result = adminMemberDao.updatePassword(adminMemberVo.getA_m_id(),newPassword);
			
			//관리자에게 새 비밀번호를 메일로 발송
			sendNewPasswordByMail(adminMemberVo.getA_m_mail(), newPassword);
		
		
		}
		
		
		return result;
	}

	private void sendNewPasswordByMail(String a_m_mail, String newPassword) {
		
		final MimeMessagePreparator mimeMessagePreparator =
				new MimeMessagePreparator() {
					
					@Override
					public void prepare(MimeMessage mimeMessage) throws Exception {
						final MimeMessageHelper helper =
								new MimeMessageHelper(mimeMessage, true, "utf-8");
						
						helper.setTo(a_m_mail);
						helper.setSubject("[한국 도서관] 새 비밀번호 발송");
						helper.setText("새 비밀번호는 :" + newPassword, true);
						
					}
				};
				
				javaMailSenderImpl.send(mimeMessagePreparator);
	}

	private String createPassword() {
		System.out.println("[AdminMemberService] createPassword");
		
		char[] chars = new char[] {'0','1','2','3','4','5','6','7','8','9',
				'a','b','c','d','e','f','g','h','i','j','k','l','n','m','o','p','q','r','s','t','u','v','w','x','y','z'
		};
		
		StringBuffer stringBuffer = new StringBuffer();
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.setSeed(new Date().getTime());
		
		int index = 0;
		int length = chars.length;
		
		for (int i=0; i<8; i++) {
			index = secureRandom.nextInt(length);
			
			if(index%2 == 0)
				stringBuffer.append(String.valueOf(chars[index]).toUpperCase());
			else
				stringBuffer.append(String.valueOf(chars[index]).toLowerCase());
		}
		
		System.out.println("NEW PASSWORD : " + stringBuffer.toString());
		
		return stringBuffer.toString();
	}
	
}
