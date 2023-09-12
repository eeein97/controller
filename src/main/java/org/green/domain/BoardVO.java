package org.green.domain;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data //getter/setter 자동 생성
public class BoardVO {
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updatedate;
	private Long rnum;
	
	private List<BoardAttachVO> attachList;
}
