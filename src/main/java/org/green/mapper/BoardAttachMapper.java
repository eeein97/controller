package org.green.mapper;

import java.util.List;

import org.green.domain.BoardAttachVO;

public interface BoardAttachMapper {
	//���
	public void insert(BoardAttachVO vo);
	//����
	public void delete(String uuid);
	//��ȸ
	public List<BoardAttachVO> findByBno(Long bno);
	//�Խñ۹�ȣ�� �ش��ϴ� ���ڵ� ����
	public void deleteAll(Long bno);
	
}
