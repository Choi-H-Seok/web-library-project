package com.company.library.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {
	//컨트롤러의 역할 : 클라이언트의 요청처리, 적절한 페이지를 리턴(String 타입)
	
	//@RequestMapping(value={"", "/"}, method = RequestMethod.GET)
	@GetMapping(value={"", "/"})
	public String home() {
		System.out.println("[AdminHomeController] home()");
		String nextPage = "admin/home";
		return nextPage;
	}
	
}
