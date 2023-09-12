package org.green.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.text.SimpleDateFormat;

import org.green.domain.AttachFileDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {
	// 년/월/일 폴더 생성하기
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);	//2023-07-27이 str에 담겨있음
		//운영체제마다 파일 경로 구분자가 다름
		//OS에 따른 경로 차이를 신경쓰지 않고 파일 경로를 지정 File.separator
		return str.replace("-", File.separator);	//2027-07-27 -> 2023/07/27
			//replace : "-"문자를 "/"로 바꿔줌
	}
	//파일이 이미지 파일인지 확인하기
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	@GetMapping("/uploadForm")
	public void uploadForm() {
		log.info("upload form");
	}
	//업로드하는 파일을 처리, 여러개가 업로드 될 수도 있어서 []타입으로 처리
	@PostMapping("/uploadFormAction")
	public void uploadFormpost(MultipartFile[] uploadFile, Model model) {
		//저장 위치
		String uploadFolder = "C:\\upload";
		for(MultipartFile multipartfile : uploadFile) {
			log.info("------------------------------");
			log.info("파일 이름 : "+ multipartfile.getOriginalFilename());
			log.info("파일 크기 : "+ multipartfile.getSize());

			//uploadFolder(경로)에 multipartfile.getOriginalFilename()(저장된 이름)으로 저장하겠다.
			File saveFile = new File(uploadFolder, multipartfile.getOriginalFilename());
			try {
				multipartfile.transferTo(saveFile); //transferTo() 해줘야 저장됨
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("upload ajax");
	}
	@PostMapping("/uploadAjaxAction")
				//객체 타입으로 리턴					 //변수명		
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxpost (MultipartFile[] uploadFile) {
		List<AttachFileDTO> list = new ArrayList<AttachFileDTO>();
		log.info("===============================================");
		log.info("upload ajax post..................");
		String uploadFolder = "C:\\upload";

		String uploadFolderPath = getFolder();	//2023/07/27을 문자열로 받아옴
		//폴더 생성하기
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		log.info("uploadPath : " + uploadPath);
			
		// 없으면 폴더를 만듦
		//~.exists() : ~가 있는지 확인
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		for(MultipartFile multipartfile : uploadFile) {
			AttachFileDTO attachDTO = new AttachFileDTO();
			log.info("------------------------------");
			log.info("파일 이름 : "+ multipartfile.getOriginalFilename());
			log.info("파일 크기 : "+ multipartfile.getSize());
			
			String uploadFileName = multipartfile.getOriginalFilename();
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
			log.info("only file name : " + uploadFileName);
			//파일이름 필드에 할당
			attachDTO.setFileName(uploadFileName);
			//중복방지 UUID 사용
			//임의의 값을 생성
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			//uploadFolder(경로)에 multipartfile.getOriginalFilename()(저장된 이름)으로 저장하겠다.
			File saveFile = new File(uploadPath, uploadFileName);
			try {
				multipartfile.transferTo(saveFile);
				//파일이 이미지 파일이라면 썸네일 이미지 생성하기
				if(checkImageType(saveFile)) { //이미지면 true리턴
					attachDTO.setImage(true);
					FileOutputStream thumbnail = new FileOutputStream(new File(
							uploadPath, "s_" + uploadFileName ));
					Thumbnailator.createThumbnail(multipartfile.getInputStream(),thumbnail, 100, 100);
																						//이미지 크기 100*100
					thumbnail.close();
				}
				//uuid 필드에 할당
				attachDTO.setUuid(uuid.toString());
				//이미지 경로 필드에 할당
				attachDTO.setUploadPath(uploadFolderPath);
				//리스트 형태로 이미지 마다 추가
				list.add(attachDTO);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new ResponseEntity<List<AttachFileDTO>>(list, HttpStatus.OK);
	}
	//썸네일 데이터 전송하기
	//fileName을 전달받고 byte전송해줌
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName) {
		log.info("filename"+fileName);
		File file = new File("C:\\upload\\"+fileName);
		ResponseEntity<byte[]> result = null;
		HttpHeaders header = new HttpHeaders();
		try {
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<byte[]>	(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	//첨부파일 삭제
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type) {
		log.info("deleteFile : " + fileName);
		File file;
		try {
			file = new File("C:\\upload\\"+ URLDecoder.decode(fileName,"UTF-8"));
			file.delete();
			//이미지 파일이라면 원본 이미지도 제거 
			if(type.equals("image")) {
				String largeFileName = file.getAbsolutePath().replace("s_","");
				log.info("largeFileName : " + largeFileName);
				file = new File(largeFileName);
				file.delete();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
}

