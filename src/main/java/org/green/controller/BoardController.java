package org.green.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.sound.midi.Patch;

import org.green.domain.BoardAttachVO;
import org.green.domain.BoardVO;
import org.green.domain.Criteria;
import org.green.domain.PageDTO;
import org.green.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor //������ ���� 
public class BoardController {
	private BoardService service;
	@GetMapping("/list2")
	public void list2(Criteria cri, Model model) {
		model.addAttribute("list",service.getList(cri));
		int total = service.getTotal(cri);
		model.addAttribute("pageMaker", new PageDTO(cri, total));
	}

	//�Խñ� ����Ʈ list?pageNum=1&amount=10
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		model.addAttribute("list",service.getList(cri));
		int total = service.getTotal(cri);
		model.addAttribute("pageMaker", new PageDTO(cri, total));
	}
	//�Խñ� ��� ������ ����
	@GetMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public void regiter() {

	}
	//�Խñ� ��� ó��
	@PostMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public String regiser(BoardVO board, RedirectAttributes rttr) {
		//RedirectAttributes
		//����۾��� ���� �� �ٽ� ���ȭ������ �̵��ϱ� ����
		//�߰������� ���Ӱ� ����� �Խù��� ��ȣ�� ���� �����ϱ� ���� ���
		log.info("================================================================================");
		log.info("register : "+board);
		log.info("================================================================================");
		service.register(board);
		rttr.addAttribute("result", board.getBno());
		return "redirect:/board/list";
	}
	//�Խñ� 1����ȸ
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("bno") Long bno, //@RequestParam("bno") �����̸� �ٸ� �� ����
			@ModelAttribute("cri") Criteria cri,
			Model model) {
		log.info("get");
		model.addAttribute("board", service.get(bno));
	}

	//������û
	@PostMapping("/modify")
	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("modify : " + board);
		if(service.modify(board)) {
			rttr.addAttribute("result", "����");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/board/list";
	}

	//������û ó��
	@PostMapping("/remove")
	public String remove(Long bno, RedirectAttributes rttr) {
		
		log.info("remove : " + bno);
		List<BoardAttachVO> attachList = service.getAttachList(bno);
		if(service.remove(bno)) {
			//���� ���� �޼ҵ� ȣ��
			rttr.addAttribute("result", "����");
			deleteFiles(attachList);
		}
		return "redirect:/board/list";
	}
	@GetMapping(value="/getAttachList", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno) { 
		return new ResponseEntity<>(service.getAttachList(bno), HttpStatus.OK);
	}
	//���� ���� �޼ҵ�
	private void deleteFiles(List<BoardAttachVO> attachList) {
		if(attachList == null || attachList.size() == 0) {
			return;
		}
		attachList.forEach(attach -> {
			Path file = Paths.get("C:\\upload\\"+attach.getUploadPath()+attach.getUuid()+"_"+attach.getFileName());
			try {
				Files.deleteIfExists(file);
				//�̹����� ��� ����� �̹����� ����
				if(Files.probeContentType(file).startsWith("image")) {
					Path thumbNail = Paths.get("C:\\upload\\"+attach.getUploadPath()+attach.getUuid()+"_"+attach.getFileName());
					Files.delete(thumbNail);
				} }catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		});
	}
}

