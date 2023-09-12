package org.green.controller;

import org.green.domain.AuthVO;
import org.green.domain.MemberVO;
import org.green.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import oracle.jdbc.proxy.annotation.Post;

@Controller
@Log4j
@AllArgsConstructor
public class CommonController {
	private MemberService service;
	@GetMapping("/register_member")
	public void registerMember() {
		log.info("ȸ������");
    }
	
	@PostMapping("/register_member")
	public String registerMember(MemberVO mvo, AuthVO avo, RedirectAttributes rttr) {
		log.info("=======================================");
		log.info("mvo : "+ mvo );
		log.info("=======================================");
		log.info("avo : "+ avo );
		log.info("=======================================");
		service.register(mvo, avo);
		rttr.addAttribute("result", "���");
		return "redirect:/board/list";
    }
	
	@GetMapping("/accessError")
	public void accessDenied(Authentication auth, Model model) {
		log.info("���ٰź� : " + auth);
		model.addAttribute("msg","���ٰź�");

	}
	@GetMapping("/customLogin")
	public void logininput(String erorr,String logout, Model model) {
		log.info("���� : "+ erorr);
		log.info("�α׾ƿ� : "+ logout);

		//���� �������
		if(erorr != null) {
			model.addAttribute("error","�α��ο���");
		}
		if(logout != null) {
			model.addAttribute("logout","�α׾ƿ�!");
		}
	}	
	@GetMapping("/customLogout")
	public void logoutGet() {
		log.info("�α׾ƿ�");
	}
	
}
