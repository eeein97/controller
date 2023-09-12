package org.green.service;

import java.util.List;

import org.green.domain.BoardVO;
import org.green.domain.Criteria;
import org.green.domain.GalleryVO;

public interface GalleryService {
	//등록
	public void register(GalleryVO gallery);
	//게시글 1개 조회
	public GalleryVO get(int gno);
	//수정
	public boolean modify(GalleryVO gallery);
	//삭제
	public boolean remove(int gno);
	//목록 조회
	public List<GalleryVO> getList();
}
