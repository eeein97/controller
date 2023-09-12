package org.green.service;

import java.util.List;

import org.green.domain.BoardAttachVO;
import org.green.domain.BoardVO;
import org.green.domain.Criteria;

public interface BoardService {
	//��� insert
	public void register(BoardVO board);
	//�Խñ� 1�� ��ȸ select
	public BoardVO get(Long bno);
	//�����ϱ�
	public boolean modify(BoardVO board);
	//�����ϱ�
	public boolean remove(Long bno);
	//�Խñ� ��� ��ȸ
	public List<BoardVO> getList(Criteria cri);
	//�Խñ� ���� ��ȸ
	public int getTotal(Criteria cri);
	
	public List<BoardAttachVO> getAttachList(Long bno);
}

