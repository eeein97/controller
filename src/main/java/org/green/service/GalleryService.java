package org.green.service;

import java.util.List;

import org.green.domain.BoardVO;
import org.green.domain.Criteria;
import org.green.domain.GalleryVO;

public interface GalleryService {
	//���
	public void register(GalleryVO gallery);
	//�Խñ� 1�� ��ȸ
	public GalleryVO get(int gno);
	//����
	public boolean modify(GalleryVO gallery);
	//����
	public boolean remove(int gno);
	//��� ��ȸ
	public List<GalleryVO> getList();
}
