package org.green.service;

import java.util.List;

import org.green.domain.BoardAttachVO;
import org.green.domain.BoardVO;
import org.green.domain.Criteria;

public interface BoardService {
	//등록 insert
	public void register(BoardVO board);
	//게시글 1개 조회 select
	public BoardVO get(Long bno);
	//수정하기
	public boolean modify(BoardVO board);
	//삭제하기
	public boolean remove(Long bno);
	//게시글 목록 조회
	public List<BoardVO> getList(Criteria cri);
	//게시글 개수 조회
	public int getTotal(Criteria cri);
	
	public List<BoardAttachVO> getAttachList(Long bno);
}

